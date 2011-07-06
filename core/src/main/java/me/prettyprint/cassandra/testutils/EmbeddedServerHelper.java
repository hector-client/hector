package me.prettyprint.cassandra.testutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.net.URL;

import org.apache.commons.lang.text.StrSubstitutor;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.ConfigurationException;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.config.KSMetaData;
import org.apache.cassandra.contrib.utils.service.CassandraServiceDataCleaner;
import org.apache.cassandra.db.commitlog.CommitLog;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.cassandra.thrift.CassandraDaemon;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Ran Tavory (rantav@gmail.com)
 * @author Felipe Seré (felipesere@gmail.com)
 */

public class EmbeddedServerHelper {
  private static Logger log = LoggerFactory.getLogger(EmbeddedServerHelper.class);

  private EmbeddedCassandraService cassandra;
  private String log4jFile = null;
  private final String clusterName;
  private final String folder;
  private final int port;
  private boolean useDefaultLog4j;
  
  static CassandraDaemon cassandraDaemon;
  
  public EmbeddedServerHelper(String log4jFile, String clusterName, String folder, int port) {
  	this.log4jFile = log4jFile;
  	this.clusterName = clusterName;
  	this.folder = folder;
  	this.port = port;
  	this.useDefaultLog4j = false;
  	
  }
  	
  public EmbeddedServerHelper() {
  	this("/log4j.properties", "Test Cluster", "tmp",9170);
  	
  	this.useDefaultLog4j = true;
  }
  
  // Kept this constructor to be compatible
  public EmbeddedServerHelper(String yamlFile) {
  	this();
  }
  
  static ExecutorService executor = Executors.newSingleThreadExecutor(); 

  /**
   * Set embedded cassandra up and spawn it in a new thread.
   * 
   * @throws TTransportException
   * @throws IOException
   * @throws InterruptedException
   */
  public void setup() throws TTransportException, IOException,
      InterruptedException, ConfigurationException {
    
    rmdir(folder);
    
    // Different copy strategies if he use the default log4j in the packge
    // or one provided with a URL String
    if( useDefaultLog4j) {
    	copy(log4jFile, folder);
    }
    else {
    	copyFile(log4jFile, folder);
 	}
 	// retrieve the cassandra.yaml.template from the JAR
 	URL url =  EmbeddedServerHelper.class.getResource("cassandra.yaml.template");
 	
 	if(url == null) {
 		throw new RuntimeException("Did not find the cassandra.yaml.template");
 	}
 	
 	// Create a new file in the new folder without .template containing the following settings:
 	File cassandraConfig = createConfigFromTemplate(new File(url.getFile()), clusterName, folder, port);
    
    System.setProperty("cassandra.config", "file:" + folder + "/cassandra.yaml");
    System.setProperty("log4j.configuration", "file:" + folder + "/" + log4jFile.substring(log4jFile.lastIndexOf("/") +1));
    System.setProperty("cassandra-foreground","true");

    cleanupAndLeaveDirs();
    loadSchemaFromYaml();
    log.info("Starting executor");
    
    executor.execute(new CassandraRunner());
    log.info("Started executor");
    try
    {
        TimeUnit.SECONDS.sleep(3);
        log.info("Done sleeping");
    }
    catch (InterruptedException e)
    {
        throw new AssertionError(e);
    }
  }



  public static void teardown() {
    //if ( cassandraDaemon != null )
      //cassandraDaemon.stop();
    executor.shutdown();
    executor.shutdownNow();
    log.info("Teardown complete");
  }

  private static void rmdir(String dir) throws IOException {
    File dirFile = new File(dir);
    if (dirFile.exists()) {
      FileUtils.deleteRecursive(new File(dir));
    }
  }

/**
   * Copies a resource from within the jar to a directory.
   * 
   * @param resource
   * @param directory
   * @throws IOException
   */
  private static void copy(String resource, String directory) throws IOException {
    mkdir(directory);
    InputStream is = EmbeddedServerHelper.class.getResourceAsStream(resource);
    
    if( is == null) {
    	throw new RuntimeException("Did not find resource: " + resource);
    }
    
    String fileName = resource.substring(resource.lastIndexOf("/") + 1);
    File file = new File(directory + System.getProperty("file.separator")
        + fileName);
    OutputStream out = new FileOutputStream(file);
    byte buf[] = new byte[1024];
    int len;
    while ((len = is.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    out.close();
    is.close();
  }
 
  /**
   * Copies a resource from within the jar to a directory.
   * 
   * @param resource
   * @param directory
   * @throws IOException
   */
  private static void copyFile(String resource, String directory)
      throws IOException {
    mkdir(directory);
    
    String fileName = resource.substring(resource.lastIndexOf("/") + 1);
    File resourceFile = new File(resource);
    if (! resourceFile.exists() ) {
    	throw new RuntimeException("CopyFile did not find "+ resource);
    }
    
    InputStream is = new FileInputStream(resourceFile);
    
    File file = new File(directory + System.getProperty("file.separator")
        + fileName);
    OutputStream out = new FileOutputStream(file);
    byte buf[] = new byte[1024];
    int len;
    
    if(is == null) {
    	throw new RuntimeException("Did not find " + resource );
    }
    
    while ((len = is.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    out.close();
    is.close();
  }
  

  /**
   * Creates a directory
   * 
   * @param dir
   * @throws IOException
   */
  private static void mkdir(String dir) throws IOException {
    FileUtils.createDirectory(dir);
  }
  

  public static void cleanupAndLeaveDirs() throws IOException
  {
      mkdirs();
      cleanup();
      mkdirs();
      CommitLog.instance.resetUnsafe(); // cleanup screws w/ CommitLog, this brings it back to safe state
  }

  public static void cleanup() throws IOException
  {
      // clean up commitlog
      String[] directoryNames = { DatabaseDescriptor.getCommitLogLocation(), };
      for (String dirName : directoryNames)
      {
          File dir = new File(dirName);
          if (!dir.exists())
              throw new RuntimeException("No such directory: " + dir.getAbsolutePath());
          FileUtils.deleteRecursive(dir);
      }

      // clean up data directory which are stored as data directory/table/data files
      for (String dirName : DatabaseDescriptor.getAllDataFileLocations())
      {
          File dir = new File(dirName);
          if (!dir.exists())
              throw new RuntimeException("No such directory: " + dir.getAbsolutePath());
          FileUtils.deleteRecursive(dir);
      }
  }

  public static void mkdirs()
  {
      try
      {
          DatabaseDescriptor.createAllDirectories();
      }
      catch (IOException e)
      {
          throw new RuntimeException(e);
      }
  }  
  
  private static List<String> readLinesOfResource(String filepath) {
  	
  	List<String> result = new ArrayList<String>();
  	
  	BufferedReader br = null;
  	
  	
  	
  	String filename = filepath.substring(filepath.lastIndexOf("/")+1);
  	
  	try {
  		br = new BufferedReader(new InputStreamReader(EmbeddedServerHelper.class.getResourceAsStream(filename)));
  	
  		String line = null;
  		while((line = br.readLine()) != null ) {
  			result.add(line);
  		}
  	
  	}
  	catch (Exception e) {
  		log.info("Exception when reading file: " + e.getMessage());
  	}
  	
  	if(result.size() == 0 ) {
  		throw new RuntimeException("Didn't read any lines? file: " + filepath);
  	}
  	
  	return result;
  }
  
  private static File writeLines(String filename, List<String> lines) {
  	
  	if(lines.size() == 0 ) {
  		throw new RuntimeException("Instructed to write 0 lines into file");
  	}
  	
  	File file = new File(filename);
  	
  	try {
  		if (file.exists() ) {
  			file.delete();
  		}
  		file.createNewFile();
  	
  		PrintWriter pw = new PrintWriter(new FileWriter(file));
  	
  		for(String line : lines) {
  			pw.println(line);
  		}
  		
  	}catch (Exception e) {
  		throw new RuntimeException("Exception when writing file: " + e.getMessage() + " " + file.getAbsolutePath());
  		
  	}
  	
  	return file;
  }
  	
  
  public static void loadSchemaFromYaml()  
  {
    EmbeddedSchemaLoader.loadSchema();
      
  }  
  
  private static File createConfigFromTemplate(File configTemplate, String clusterName, String directory, int port) {

		final String configFileName = "cassandra.yaml";
		
		Map values = new HashMap();
		values.put("cluster_name", clusterName);
		values.put("dir", directory);
		values.put("port", port);
		
		StrSubstitutor sub = new StrSubstitutor(values);
		
		List<String> lines = readLinesOfResource(configTemplate.getAbsolutePath());
		List<String> result = new ArrayList<String>();
		
		if(lines == null) {
			throw new RuntimeException("Could not read lines from template");
		}
		
		for(String line : lines) {
			result.add(sub.replace(line));
		}

		File file = writeLines(directory+"/cassandra.yaml", result);
		
		if(file == null) {
			throw new RuntimeException("Error writing new cassanda.yaml");
		}
		
		return file;
	}

  
  class CassandraRunner implements Runnable {    
  
    @Override
    public void run() {

      cassandraDaemon = new CassandraDaemon();
     
      cassandraDaemon.activate();

    }
    
  }
}

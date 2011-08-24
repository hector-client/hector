package me.prettyprint.hector.testutils;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.net.URL;

import org.apache.commons.lang.text.StrSubstitutor;

import org.apache.cassandra.config.ConfigurationException;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.commitlog.CommitLog;
import org.apache.cassandra.io.util.FileUtils;
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

  private static EmbeddedServerConfigurator configurator;

  CassandraDaemon cassandraDaemon;

  public EmbeddedServerHelper() {
    this(new EmbeddedServerConfigurator());
  }

  // Main constructor that is called from all other constructors
  public EmbeddedServerHelper(EmbeddedServerConfigurator cfg) {
    this.configurator = cfg;
  }
  
  // Kept this constructor to be compatible
  public EmbeddedServerHelper(String yamlFile) {
  	this(EmbeddedServerConfigurator.createFromYaml(yamlFile));
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

    // Delete the old folder and create a new one
    rmdir(configurator.getFolder());
    mkdir(configurator.getFolder());

    // Copy the log4j file into the destination folder specified in the configurator
    copy(configurator);
 	
 	// Create a new cassandra config based on the information in the EmbeddedServerConfigurator
 	loadConfigIntoDestination(configurator);

    System.setProperty("cassandra.config", "file:" + configurator.getFolder() + File.separator + "cassandra.yaml");
    System.setProperty("log4j.configuration", "file:" + configurator.getFolder() + File.separator + "log4j.properties");
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

  public static void teardown() throws IOException {
    executor.shutdown();
    executor.shutdownNow();

    rmdir(configurator.getFolder());
    log.info("Teardown complete");
  }

  private static void rmdir(String dir) throws IOException{
    if(dir.contains(File.separator)) {
        dir = dir.substring(0,dir.indexOf(File.separator));
    }
    log.info("Deleting " + dir);
    File dirFile = new File(dir);
    if (dirFile.exists()) {
        FileUtils.deleteRecursive(dirFile);

    }
  }

    /**
     * Copy the log4j file into the the destination folder specified in configurator
     * @param configurator Holds information where to copy the log4j and whether to use the packaged one
     */
    private static void copy(EmbeddedServerConfigurator configurator) {
        String dest = configurator.getFolder() + File.separator + "log4j.properties";
        InputStream is = null;
        if(configurator.isUsePackagedLog4j()) {
            is = EmbeddedServerHelper.class.getResourceAsStream(File.separator + "log4j.properties");
        }
        else {
            try {
                is = new FileInputStream(configurator.getLog4jPath());
            }
            catch (FileNotFoundException fnfe) {
                is = EmbeddedServerHelper.class.getResourceAsStream(File.separator + "log4j.properties");
            }
        }

        File f = new File(dest);
        OutputStream out;
        try {
              out =  new FileOutputStream(f);
        }
        catch( FileNotFoundException fnfe ){
            try {
                f.createNewFile();
                out = new FileOutputStream(f);
            }catch (IOException ioe) {
                throw new RuntimeException("Caught an IOException " + ioe.getLocalizedMessage());
            }
        }

        byte buf[] = new byte[1024];
        int len;

        try {
        while ((len = is.read(buf)) > 0) {
          out.write(buf, 0, len);
        }
        out.close();
        is.close();
        }
        catch (IOException ioe) {
            throw new RuntimeException("Caught an IOException " + ioe.getLocalizedMessage());
        }
    }

  /**
   * Creates a directory
   * 
   * @param dir
   * @throws IOException
   */
  private static void mkdir(String dir) throws IOException {
    FileUtils.createDirectory(dir);
    log.info("Creating directory: " + dir);
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
  	
  	BufferedReader br;
  	
  	try {
  		br = new BufferedReader(new InputStreamReader(EmbeddedServerHelper.class.getResourceAsStream(filepath), "UTF-8"));
  	
  		String line;
  		while((line = br.readLine()) != null ) {
  			result.add(line);
  		}
  	
  	}
  	catch (Exception e) {
  		log.info("Exception when reading file: " + e.getMessage());
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
  
  //private static File loadConfigIntoDestination(File configTemplate, String clusterName, String directory, int port) {
  private static void loadConfigIntoDestination(EmbeddedServerConfigurator cfg) {

        URL url =  EmbeddedServerHelper.class.getResource("cassandra.yaml.template");


		final String configFileName = "cassandra.yaml";
		final String pathSeparator = File.separator;


		Map<String,String> values = new HashMap<String,String>();
		values.put("cluster_name",  cfg.getClusterName());
		values.put("dir",           cfg.getFolder());
		values.put("port",          Integer.toString(cfg.getThriftPort()));
		
		StrSubstitutor sub = new StrSubstitutor(values);


		List<String> lines = readLinesOfResource(new File(url.getFile()).getName());
		List<String> result = new ArrayList<String>();
		
		for(String line : lines) {
			result.add(sub.replace(line));
		}
	
	
		writeLines(cfg.getFolder()+pathSeparator+configFileName, result);
	}

  
  class CassandraRunner implements Runnable {    
  
    @Override
    public void run() {

      cassandraDaemon = new CassandraDaemon();
     
      cassandraDaemon.activate();

    }
    
  }
}

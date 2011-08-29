package me.prettyprint.hector.testutils;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author felipesere@gmail.com
 */

public class EmbeddedServerConfigurator {
    private String clusterName;
    private String folder;
    private int thriftPort;

    private String yamlPath;
    private String log4jPath;
    private boolean usePackagedLog4j;

    public EmbeddedServerConfigurator() {
        this.setClusterName("Test Cluster");
        this.setFolder("tmp");
        this.setThriftPort(9170);
        this.setLog4jPath("log4j.properties");
        this.setUsePackagedLog4j(true);
    }

    public static EmbeddedServerConfigurator createFromYaml(String path) {
        EmbeddedServerConfigurator result = new EmbeddedServerConfigurator();
        if(path != null) {
            // We need to process the yaml file to fill in the missing variables
            Yaml yaml = new Yaml();
            try {
                FileInputStream fis = new FileInputStream(path);
                Map map = (Map) yaml.load(fis);

                Set<String> keys = map.keySet();

                result.setClusterName((String) map.get("cluster_name"));
                result.setThriftPort((Integer) map.get("rpc_port"));
                List<String> dirs = (List<String>) map.get("data_file_directories");

                result.setFolder(dirs.get(0).substring(0,dirs.get(0).indexOf(File.separator)));

            }
            catch( FileNotFoundException fnf) {
                throw new RuntimeException("File not found: " + fnf.getMessage());
            }

        }

        return result;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public int getThriftPort() {
        return thriftPort;
    }

    public void setThriftPort(int thriftPort) {
        this.thriftPort = thriftPort;
    }

    public String getYamlPath() {
        return yamlPath;
    }

    public void setYamlPath(String yamlPath) {

    }

    public String getLog4jPath() {
        return log4jPath;
    }

    public void setLog4jPath(String log4jPath) {
        this.log4jPath = log4jPath;
    }

    public boolean isUsePackagedLog4j() {
        return usePackagedLog4j;
    }

    public void setUsePackagedLog4j(boolean usePackagedLog4j) {
        this.usePackagedLog4j = usePackagedLog4j;
    }
}



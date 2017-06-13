package fr.insalyon.creatis.vip.cli.vue;

import static java.lang.System.exit;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import fr.insalyon.creatis.vip.cli.model.InfoExecution;
import fr.insalyon.creatis.vip.cli.model.InfoExecutionDAO;
import fr.insalyon.creatis.vip.java_client.model.Execution;
import fr.insalyon.creatis.vip.java_client.model.Pipeline;
import fr.insalyon.creatis.vip.java_client.model.PlatformProperties;
import org.apache.commons.io.FileUtils;

public class UtilIO {

    public static String GetApiKey(File apiKeyFile) {
        String apiKeyValue = "";
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(apiKeyFile));
            BufferedReader bufferedReader = new BufferedReader(read);
            try {
                apiKeyValue = bufferedReader.readLine();
            } catch (IOException ex) {
                System.err.println("Error in the key file");
                bufferedReader.close();
                exit(0);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            // Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null,
            // ex);
            System.err.println("Key file not found.");
            exit(0);
        }

        return apiKeyValue;
    }

    public static void printResultatExecute(Execution execution, String repertoire) {


        PrintWriter writer = new PrintWriter(System.out);
        writer.println("identifier: " + execution.getIdentifier());
        writer.println("directory: " + repertoire);
        writer.close();

    }

    public static void printExecutionDetial(Execution execution) {


        PrintWriter writer = new PrintWriter(System.out);
        writer.println(execution.getStatus());
        writer.close();

    }

    public static void printListInfoExecutions(List<InfoExecution> listExecution) {
        for (InfoExecution info : listExecution) {
            System.out.println(info);
        }

    }

    public static void printPlatformProperties(PlatformProperties platformproperty) {
        System.out.println("Platform name: " + platformproperty.getPlatformName());
        System.out.println("Platform description: " + platformproperty.getPlatformDescription());
        System.out.println("Api version supported: " + platformproperty.getSupportedAPIVersion());
        System.out.println("Contact email: " + platformproperty.getEmail());
    }

    public static void printPipelinesList(List<Pipeline> pipelinesList) {
        for (Pipeline pipeline : pipelinesList) {

            System.out.println("Name: " + pipeline.getName());
            System.out.println("Version: " + pipeline.getVersion());
            System.out.println("Can execute: " + pipeline.getCanExecute() + "\r\n");
        }
    }

    public static void downloadFile(String address) {
        URL url = null;
        try {
            url = new URL(address);
            File file=new File("test.txt");
            FileUtils.copyURLToFile(url,file);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

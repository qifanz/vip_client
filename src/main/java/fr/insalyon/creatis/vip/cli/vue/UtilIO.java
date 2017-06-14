package fr.insalyon.creatis.vip.cli.vue;

import static java.lang.System.exit;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import fr.insalyon.creatis.vip.cli.model.InfoExecution;
import fr.insalyon.creatis.vip.java_client.model.Execution;
import fr.insalyon.creatis.vip.java_client.model.Pipeline;
import fr.insalyon.creatis.vip.java_client.model.PlatformProperties;

public class UtilIO {
    public static String apiKeyValue = "";
    public static String GetApiKey(File apiKeyFile) {

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

    public static void downloadFile(List<String> urls) {


        try {
            for (String url:urls) {
                URL fileUrl = new URL(url);
                System.out.println(url);
                HttpURLConnection httpConnection = (HttpURLConnection) fileUrl.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("apiKey", apiKeyValue);
                InputStream response = httpConnection.getInputStream();

                InputStream decodedResponse = Base64.getDecoder().wrap(response);
                File file=new File(url.substring(url.lastIndexOf('/')+1));
                file.createNewFile();
                OutputStream outputStream =
                        new FileOutputStream(file);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = decodedResponse.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                System.out.println("Done!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

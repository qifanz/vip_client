package fr.insalyon.creatis.vip.cli.Vue;

import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import fr.insalyon.creatis.vip.java_client.model.Execution;

public class UtilIO {

	private static BufferedReader bufferedReader;

	public static String GetApiKey(File apiKeyFile) {
		String apiKeyValue = "";
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(apiKeyFile));
			bufferedReader = new BufferedReader(read);
			try {
				apiKeyValue = bufferedReader.readLine();
			} catch (IOException ex) {
				// Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null,
				// ex);
				System.err.println("Error in the key file");
				exit(0);

			}
		} catch (FileNotFoundException ex) {
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
}

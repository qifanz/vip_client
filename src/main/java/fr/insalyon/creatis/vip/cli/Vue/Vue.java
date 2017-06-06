/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.Vue;

import fr.insalyon.creatis.vip.cli.Action.Action;
import fr.insalyon.creatis.vip.cli.Action.GetExecutionAction;
import fr.insalyon.creatis.vip.cli.Action.GetPipelineAction;
import fr.insalyon.creatis.vip.cli.Action.GetPlatformPropertyAction;
import fr.insalyon.creatis.vip.cli.Action.InitAndExecuteAction;
import fr.insalyon.creatis.vip.cli.Action.ListExecutionAction;
import fr.insalyon.creatis.vip.cli.Action.ListPipelinesAction;
import fr.insalyon.creatis.vip.java_client.ApiClient;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;
import fr.insalyon.creatis.vip.java_client.model.Pipeline;
import fr.insalyon.creatis.vip.java_client.model.PlatformProperties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author qzhang
 */
public class Vue {

	public static void main(String args[]) {
		// initialize the client and api key
		File apiKeyFile = new File("ApiKey.txt");
		String apiKeyValue = UtilIO.GetApiKey(apiKeyFile);

		ApiClient client = new ApiClient();
		client.setBasePath("http://vip.creatis.insa-lyon.fr/rest");
		client.setApiKey(apiKeyValue);
		DefaultApi api = new DefaultApi(client);
		Action action;

		switch (args[0]) {
		case "execute":
			action = new InitAndExecuteAction(api);

			String pipelineIdentifier = args[1];
			Map<String, Object> parameters = new HashMap<String, Object>();
			for (int i = 2; i < args.length; i += 2) {
				parameters.put(args[i].substring(2), args[i + 1]);
			}
			Date actualTime=new Date();
			String repertoire="/vip/Home/cli/";
			repertoire+=actualTime.getYear()+1900;
			repertoire+=actualTime.getMonth()+1;
			repertoire+=actualTime.getDate();
			repertoire+=actualTime.getHours();
			repertoire+=actualTime.getMinutes();
			repertoire+=actualTime.getSeconds();
			parameters.put("results-directory", repertoire);	
			((InitAndExecuteAction) action).setExecution("vip-cli", pipelineIdentifier, parameters);

			try {
				Object execution = action.execute();
				UtilIO.printResultatExecute((Execution) execution,repertoire);
			} catch (ApiException e) {
				e.printStackTrace();
			}

			break;
			
		case "status":
			action=new GetExecutionAction(api);
			((GetExecutionAction)action).setExecutionId(args[1]);
			try {
				Object execution=action.execute();
				UtilIO.printExecutionDetial((Execution)execution);
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;

		case "PlatformProperties":
			action = new GetPlatformPropertyAction(api);
			try {
				PlatformProperties response = (PlatformProperties) action.execute();
				System.out.println("Platform name: " + response.getPlatformName());
				System.out.println("Platform description: " + response.getPlatformDescription());
				System.out.println("Api version supported: " + response.getSupportedAPIVersion());
				System.out.println("Contact email: " + response.getEmail());
			} catch (ApiException ex) {
				Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
			}
			break;

		case "ListPipelines":
			action = new ListPipelinesAction(api);
			try {
				List<Pipeline> pipelinesList = (List<Pipeline>) action.execute();
				for (Pipeline pipeline : pipelinesList) {
					System.out.println("--------------Pipeline: ---------------");
					System.out.println("Name: " + pipeline.getName());
					System.out.println("Version: " + pipeline.getVersion());
					System.out.println("Can execute: " + pipeline.getCanExecute());
				}
			} catch (ApiException ex) {
				Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
			}
			break;
		case "GetPipeline":
			String pipelineName = args[1];
			action = new GetPipelineAction(api);
			((GetPipelineAction) action).setPipelineName(pipelineName); {
			try {
				System.out.println(action.execute());
				// System.out.println(action.execute());
			} catch (ApiException ex) {
				Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
			break;
		case "ListExecutions":
			action = new ListExecutionAction(api); {
			try {
				List<Execution> ExecutionList = (List<Execution>) action.execute();
				System.out.println(ExecutionList);
			} catch (ApiException ex) {
				Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
			break;
		default:
			System.out.println("hello");
		}
	}
}

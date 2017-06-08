/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.control;

import fr.insalyon.creatis.vip.cli.action.GetPipelineAction;
import fr.insalyon.creatis.vip.cli.action.GetPlatformPropertyAction;
import fr.insalyon.creatis.vip.cli.action.ListExecutionAction;
import fr.insalyon.creatis.vip.cli.action.ListPipelinesAction;
import fr.insalyon.creatis.vip.cli.vue.UtilIO;
import fr.insalyon.creatis.vip.java_client.ApiClient;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;
import fr.insalyon.creatis.vip.java_client.model.Pipeline;
import fr.insalyon.creatis.vip.java_client.model.PlatformProperties;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author qzhang
 */
public class Controller {

	public static void main(String args[]) {
		// initialize the client and api key
		File apiKeyFile = new File("ApiKey.txt");
		String apiKeyValue = UtilIO.GetApiKey(apiKeyFile);

		ApiClient client = new ApiClient();
		client.setBasePath("http://vip.creatis.insa-lyon.fr/rest");
		client.setApiKey(apiKeyValue);
		DefaultApi api = new DefaultApi(client);

		switch (args[0]) {
		case "execute":
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String repertoire = "/vip/Home/" + df.format(new Date());
			InitAndExecuteControl initAndExecuteControl = new InitAndExecuteControl(args, repertoire);
			UtilIO.printResultatExecute(initAndExecuteControl.execute(api), repertoire);
			break;

		case "status":
			GetExecutionControl getExecutionControl = new GetExecutionControl(args);
			UtilIO.printExecutionDetial(getExecutionControl.execute(api));
			break;
			
		case "PlatformProperties":
			GetPlatformPropertyAction getPlatformPropertyAction = new GetPlatformPropertyAction();
			try {
				PlatformProperties response = getPlatformPropertyAction.execute(api);
				UtilIO.printPlatformProperties(response);
			} catch (ApiException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
			break;

		case "ListPipelines":
			ListPipelinesAction listPipelinesAction = new ListPipelinesAction();
			try {
				List<Pipeline> pipelinesList = listPipelinesAction.execute(api);
				UtilIO.printPipelinesList(pipelinesList);
			} catch (ApiException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
			break;
	
		case "GetPipeline":
			GetPipelineAction getPipelineAction = new GetPipelineAction(args[1]);

			try {
				System.out.println(getPipelineAction.execute(api));
			} catch (ApiException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}

			break;
			
		case "ListExecutions":
			ListExecutionAction listExecutionAction = new ListExecutionAction();
			try {
				List<Execution> ExecutionList = listExecutionAction.execute(api);
				System.out.println(ExecutionList);
			} catch (ApiException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
			break;
			
		default:
			System.out.println("Option incorrect.");
		}
	}
}

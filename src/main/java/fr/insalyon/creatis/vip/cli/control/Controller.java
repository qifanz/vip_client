/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.control;

import fr.insalyon.creatis.vip.cli.model.HibernateUtil;
import fr.insalyon.creatis.vip.cli.model.InfoExecutionDAO;
import fr.insalyon.creatis.vip.cli.vue.UtilIO;
import fr.insalyon.creatis.vip.java_client.ApiClient;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import java.io.File;
import java.util.logging.Level;
import org.hibernate.Query;

/**
 *
 * @author qzhang
 */
public class Controller {

	public static void main(String args[]) {

		Arguments arguments = new Arguments(args);

		// initialize the client and api key
		File apiKeyFile = new File("../ApiKey.txt");
		String apiKeyValue = UtilIO.GetApiKey(apiKeyFile);
		ApiClient client = new ApiClient();
		client.setBasePath("http://vip.creatis.insa-lyon.fr/rest");
		client.setApiKey(apiKeyValue);
		DefaultApi api = new DefaultApi(client);

		// Hibernate initialization
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		HibernateUtil.init();

		if ((arguments.getAction()).equals("execute")) {
			InitAndExecuteControl initAndExecuteControl = new InitAndExecuteControl(api, arguments);

			UtilIO.printResultatExecute(initAndExecuteControl.execute(), initAndExecuteControl.getRepertoire());
		} else if ((arguments.getAction()).equals("status")) {
			GetExecutionControl getExecutionControl = new GetExecutionControl(api, arguments);

			UtilIO.printExecutionDetial(getExecutionControl.execute());

		} else if ((arguments.getAction()).equals("executions")) {
			InfoExecutionDAO infoDao = new InfoExecutionDAO();
			UtilIO.printListInfoExecutions(infoDao.getAllExecutions());
		}

		HibernateUtil.close();

	}

}

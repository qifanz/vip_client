/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.control;

import fr.insalyon.creatis.vip.cli.action.GetExecutionAction;
import fr.insalyon.creatis.vip.cli.action.GetResultAction;
import fr.insalyon.creatis.vip.cli.action.InitAndExecuteAction;
import fr.insalyon.creatis.vip.cli.model.HibernateUtil;
import fr.insalyon.creatis.vip.cli.model.InfoExecution;
import fr.insalyon.creatis.vip.cli.model.InfoExecutionDAO;
import fr.insalyon.creatis.vip.cli.vue.UtilIO;
import fr.insalyon.creatis.vip.java_client.ApiClient;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

/**
 * @author qzhang
 */
public class Controller {
    public final static String base = "http://vip.creatis.insa-lyon.fr/rest";

    public static void main(String args[]) {

        Arguments arguments = new Arguments(args);

        // initialize the client and api key
        File apiKeyFile = new File("../ApiKey.txt");
        String apiKeyValue = UtilIO.GetApiKey(apiKeyFile);
        ApiClient client = new ApiClient();
        client.setBasePath(base);
        client.setApiKey(apiKeyValue);
        DefaultApi api = new DefaultApi(client);

        // Hibernate initialization
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        HibernateUtil.init();
        try {
            switch ((arguments.getAction())) {
                case "execute": {
                    InitAndExecuteAction initAndExecuteAction = new InitAndExecuteAction(api, arguments);


                    Execution execution = initAndExecuteAction.execute();
                    UtilIO.printExecuteResult(execution, initAndExecuteAction.getRepertoire());
                    InfoExecution infoExecution = new InfoExecution(execution.getIdentifier(), execution.getPipelineIdentifier(),
                            execution.getStatus().toString(), initAndExecuteAction.getRepertoire(), execution.getStartDate());
                    InfoExecutionDAO infoDAO = new InfoExecutionDAO();
                    infoDAO.persist(infoExecution);


                    break;
                }
                case "status": {
                    GetExecutionAction getExecutionAction = new GetExecutionAction(api, arguments);


                    Execution execution = getExecutionAction.execute();
                    UtilIO.printExecutionDetail(execution);
                    InfoExecutionDAO infoDAO = new InfoExecutionDAO();
                    infoDAO.upadteStatusByExecutionId(execution.getIdentifier(), execution.getStatus().toString());


                    break;
                }
                case "executions":
                    InfoExecutionDAO infoDao = new InfoExecutionDAO();
                    UtilIO.printListInfoExecutions(infoDao.getAllExecutions());
                    break;
                case "download":
                    GetResultAction getResultAction = new GetResultAction(api, arguments);
                    UtilIO.downloadFile(getResultAction.execute(),arguments.getListArgs().get("").get(arguments.getListArgs().size()-1));

                    break;
            }

        } catch (ApiException e) {
            //HibernateUtil.cancelTransaction();
            System.err.println(e.getResponseBody());
        } finally {
            HibernateUtil.close();
        }
    }

}

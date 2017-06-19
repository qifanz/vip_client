/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.control;

import fr.insalyon.creatis.vip.cli.action.GetExecutionAction;
import fr.insalyon.creatis.vip.cli.action.GetResultAction;
import fr.insalyon.creatis.vip.cli.action.InitAndExecuteAction;
import fr.insalyon.creatis.vip.cli.action.KillExecutionAction;
import fr.insalyon.creatis.vip.cli.dao.HibernateUtil;
import fr.insalyon.creatis.vip.cli.model.InfoExecution;
import fr.insalyon.creatis.vip.cli.dao.InfoExecutionDAO;
import fr.insalyon.creatis.vip.cli.model.PropertyCli;
import fr.insalyon.creatis.vip.cli.vue.UtilIO;
import fr.insalyon.creatis.vip.java_client.ApiClient;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;

import java.io.File;
import java.util.logging.Level;

import static fr.insalyon.creatis.vip.cli.control.ArgType.*;
import static java.lang.System.exit;

/**
 * @author qzhang
 */
public class Controller {
    public static String apiKeyValue;

    public static void main(String args[]) {

        Arguments arguments = new Arguments(args);

        PropertyCli property=UtilIO.GetPropertyCli(new File("../property"));
        apiKeyValue =property.getApiKey();
        String base=property.getBasePath();
        String databasePosition=property.getDataBasePosition();

        ApiClient client = new ApiClient();
        client.setBasePath(base);
        client.setApiKey(apiKeyValue);
        DefaultApi api = new DefaultApi(client);

        System.out.println(apiKeyValue);
        System.out.println(base);

        // Hibernate initialization
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        HibernateUtil.init(databasePosition);
        InfoExecutionDAO infoDAO = new InfoExecutionDAO();
        try {
            switch ((arguments.getAction())) {
                case EXECUTE: {
                    InitAndExecuteAction initAndExecuteAction = new InitAndExecuteAction(api, arguments);
                    Execution execution = initAndExecuteAction.execute();
                    UtilIO.printExecuteResult(execution, initAndExecuteAction.getRepertoire());
                    InfoExecution infoExecution = new InfoExecution(execution.getIdentifier(), execution.getPipelineIdentifier(),
                            execution.getStatus().toString(), initAndExecuteAction.getRepertoire(), execution.getStartDate());
                    infoDAO.persist(infoExecution);
                    break;
                }
                case STATUS: {
                    GetExecutionAction getExecutionAction = new GetExecutionAction(api, arguments);
                    Execution execution = getExecutionAction.execute();
                    UtilIO.printExecutionDetail(execution);
                    infoDAO.upadteStatusByExecutionId(execution.getIdentifier(), execution.getStatus().toString());
                    break;
                }
                case EXECTUIONS:
                    UtilIO.printListInfoExecutions(infoDAO.getAllExecutions());
                    break;
                case DOWNLOAD:
                    GetResultAction getResultAction = new GetResultAction(api, arguments);
                    UtilIO.downloadFile(getResultAction.execute(),getResultAction.getDirectory());
                    break;
                case KILL:
                    KillExecutionAction killExecutionAction=new KillExecutionAction(api,arguments);
                    killExecutionAction.execute();
                    break;
                case INCORRECT:
                    System.err.println("Option not correct.");
                    exit(0);
                    break;

            }

        } catch (ApiException e) {
            System.err.println(e.getResponseBody());
        } finally {
            HibernateUtil.close();
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.control;

import fr.insalyon.creatis.vip.cli.action.*;
import fr.insalyon.creatis.vip.cli.dao.HibernateUtil;
import fr.insalyon.creatis.vip.cli.model.InfoExecution;
import fr.insalyon.creatis.vip.cli.dao.InfoExecutionDAO;
import fr.insalyon.creatis.vip.cli.model.PropertyCli;
import fr.insalyon.creatis.vip.cli.vue.UtilIO;
import fr.insalyon.creatis.vip.java_client.ApiClient;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;
import org.hibernate.HibernateException;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;

import static java.lang.System.exit;

/**
 * @author qzhang
 */
public class Controller {
    public final static String PROPERTIESPATH = "../cli.properties";
    public static String apiKeyValue;
    public static String base;
    public static String databasePosition;
    public static int refreshTime;

    public static void main(String args[]) {

        Arguments arguments = null;
        try {
            arguments = new Arguments(args);
        } catch (ArgumentException e) {
            System.err.println("Error while parsing arguments:");
            System.err.println(e.getMessage());
            exit(0);
        }

        PropertyCli property = UtilIO.GetPropertyCli(new File(PROPERTIESPATH));
        apiKeyValue = property.getApiKey();
        base = property.getBasePath();
        databasePosition = property.getDataBasePosition();
        refreshTime = property.getRefreshTime();

        ApiClient client = new ApiClient();
        client.setBasePath(base);
        client.setApiKey(apiKeyValue);
        DefaultApi api = new DefaultApi(client);

        //  System.out.println(apiKeyValue);
        //System.out.println(base);

        // Hibernate initialization
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        try {
            HibernateUtil.init(databasePosition);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        InfoExecutionDAO infoDAO = new InfoExecutionDAO();
        try {
            switch ((arguments.getAction())) {
                //TODO: put the dao actions into Action Classes.
                case EXECUTE: {
                    //TODO: delete automatically too old executions in the database after launched an execution.
                    if (arguments.getArgsWithFlag().get("results") == null) {
                        InitAndExecuteAction initAndExecuteAction = new InitAndExecuteAction(api, arguments);
                        Execution execution = initAndExecuteAction.execute();
                        UtilIO.printExecuteResult(execution, initAndExecuteAction.getDirectoryOnVip());
                        InfoExecution infoExecution = new InfoExecution(execution.getIdentifier(), execution.getPipelineIdentifier(),
                                execution.getStatus().toString(), initAndExecuteAction.getDirectoryOnVip(), new Date(execution.getStartDate()));
                        infoDAO.persist(infoExecution);
                    } else {
                        DoAllAction doAllAction = new DoAllAction(api, arguments);
                        UtilIO.downloadFile(doAllAction.execute(), doAllAction.getDirectory());
                    }

                    break;
                }
                case STATUS: {
                    GetExecutionAction getExecutionAction = new GetExecutionAction(api, arguments);
                    Execution execution = getExecutionAction.execute();
                    UtilIO.printExecutionStatus(execution);
                    infoDAO.upadteStatusByExecutionId(execution.getIdentifier(), execution.getStatus().toString());
                    break;
                }
                case EXECTUIONS:
                    UtilIO.printListInfoExecutions(infoDAO.getAllExecutions());
                    break;
                case RESULT:
                    GetResultAction getResultAction = new GetResultAction(api, arguments);
                    UtilIO.downloadFile(getResultAction.execute(), getResultAction.getDirectory());
                    break;
                case DELETE:
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -(refreshTime));
                    infoDAO.deleteExecution(cal.getTime());
                    break;
                case KILL:
                    KillExecutionAction killExecutionAction = new KillExecutionAction(api, arguments);
                    killExecutionAction.execute();
                    break;
                case PIPELINE:
                    GetPipelineAction getPipelineAction = new GetPipelineAction(api, arguments);
                    System.out.println(getPipelineAction.execute());
                    break;
                //this case is used to test args
                case TESTARGS:
                    System.out.println("**args without flags are**");
                    for (String argwithoutflag : arguments.getArgsWithoutFlag()) {
                        System.out.println(argwithoutflag);
                    }
                    System.out.println("**args with flags are**");
                    for (Map.Entry<String, String> entry : arguments.getArgsWithFlag().entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    System.out.println("**options are**");
                    for (String opt : arguments.getOptions()) {
                        System.out.println(opt);
                    }
                    break;


            }

        } catch (ApiException e) {
            System.err.println(e.getMessage());
        } finally {
            HibernateUtil.close();
        }
    }

}

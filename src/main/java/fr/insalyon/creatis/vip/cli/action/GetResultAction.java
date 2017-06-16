package fr.insalyon.creatis.vip.cli.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import fr.insalyon.creatis.vip.cli.control.Arguments;
import fr.insalyon.creatis.vip.cli.control.Controller;
import fr.insalyon.creatis.vip.cli.dao.InfoExecutionDAO;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

import static java.lang.System.exit;

public class GetResultAction implements Action<List<String>> {

    private String executionId;
    private String directory;
    private Arguments args;
    private DefaultApi api;


    public GetResultAction(DefaultApi api, Arguments args) {
        this.args = args;
        this.api = api;

        setExecutionId();
        setDirectory();
    }

    private void setDirectory() {
        if (args.getArgsWithoutFlag().size()>=1) {
            directory=args.getArgsWithoutFlag().get(args.getArgsWithoutFlag().size()-1);
        } else {
            System.err.println("Directory missing");
            exit(0);
        }
    }

    private void setExecutionId() {
        if (args.getArgsWithoutFlag().size()>=2) {
            executionId = args.getArgsWithoutFlag().get(0);
        } else if (args.getArgsWithoutFlag().size()==1){
            InfoExecutionDAO infoDao = new InfoExecutionDAO();
            executionId = infoDao.getLastExecution().getExecutionIdentifier();
        }
    }

    public String getDirectory() {
        return directory;
    }

    @Override
    public List<String> execute() throws ApiException {
        Map<String, List<String>> returnedFiles = api.getExecution(executionId).getReturnedFiles();
        List<String> urls = returnedFiles.get("output_file");
        List<String> usableUrls = new ArrayList<>();
        String base = api.getApiClient().getBasePath() + "/path/content?uri=vip://vip.creatis.insa-lyon.fr/vip/Home";
        for (String url : urls) {
            int pos = url.indexOf('/', 13);
            usableUrls.add(base + url.substring(pos));
        }
        return usableUrls;
    }

}
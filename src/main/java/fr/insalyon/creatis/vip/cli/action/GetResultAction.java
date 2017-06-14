package fr.insalyon.creatis.vip.cli.action;

import java.util.List;
import java.util.Map;

import fr.insalyon.creatis.vip.cli.control.Arguments;
import fr.insalyon.creatis.vip.cli.model.InfoExecutionDAO;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

public class GetResultAction implements Action<String>{

	private String executionId;
	Arguments args;
	DefaultApi api;
	public GetResultAction(DefaultApi api, Arguments args) {
		this.args=args;
		this.api=api;
		setExecutionId();
	}

	public void setExecutionId() {
		String id=args.getListArgs().get("");
		if (id!=null) {
			executionId=id;
		} else {
			InfoExecutionDAO infoDao = new InfoExecutionDAO();
			executionId= infoDao.getLastExecution().getExecutionIdentifier();
		}
	}
	@Override
	public String execute() throws ApiException {
		Map<String,List<String>> returnedFiles=api.getExecution(executionId).getReturnedFiles();
		String url=returnedFiles.get("output_file").get(0);
		String base="http://vip.creatis.insa-lyon.fr:4040/rest-test/rest/path/content?uri=vip://vip.creatis.insa-lyon.fr/vip/Home";
		int pos=url.indexOf('/',13);
		return base+url.substring(pos);
	}

}

package fr.insalyon.creatis.vip.cli.action;

import java.util.List;
import java.util.Map;

import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

public class DownloadAction implements Action<List<String>>{

	private String executionId;
	public DownloadAction	(String id){
		
		executionId=id;
	}
	@Override
	public List<String> execute(DefaultApi api) throws ApiException {
		return api.getExecutionResults(executionId,"https");
	}

}

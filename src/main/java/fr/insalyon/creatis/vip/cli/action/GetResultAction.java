package fr.insalyon.creatis.vip.cli.action;

import java.util.List;
import java.util.Map;

import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

public class GetResultAction implements Action<List<String>>{

	private String executionId;
	public GetResultAction(String id){
		
		executionId=id;
	}
	@Override
	public List<String> execute(DefaultApi api) throws ApiException {
		return api.getExecutionResults(executionId,"https");
	}

}

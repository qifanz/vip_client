package fr.insalyon.creatis.vip.cli.action;

import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;

public class GetExecutionAction implements Action<Execution> {
	String executionId;
	public GetExecutionAction(String id){
		
		executionId=id;
	}

	
	public Execution execute(DefaultApi api) throws ApiException{
		return api.getExecution(executionId);
		
	}
}

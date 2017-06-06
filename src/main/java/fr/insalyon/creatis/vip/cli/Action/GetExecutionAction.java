package fr.insalyon.creatis.vip.cli.Action;

import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

public class GetExecutionAction extends Action {
	String executionId;
	public GetExecutionAction(DefaultApi api){
		
		super(api);
	}
	public void setExecutionId(String id){
		executionId=id;
	}
	public Object execute() throws ApiException{
		return defaultApi.getExecution(executionId);
		
	}
}

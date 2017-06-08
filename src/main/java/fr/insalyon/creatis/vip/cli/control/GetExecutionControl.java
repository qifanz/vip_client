package fr.insalyon.creatis.vip.cli.control;

import fr.insalyon.creatis.vip.cli.action.GetExecutionAction;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;

public class GetExecutionControl {
	String[] args;
	public GetExecutionControl(String[]args) {
		this.args=args;
	}
	
	public Execution execute (DefaultApi api) {
		GetExecutionAction getExecutionAction = new GetExecutionAction(args[1]);
		Execution execution=null;
		try {
			 execution = getExecutionAction.execute(api);			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return execution;
	
	}
}

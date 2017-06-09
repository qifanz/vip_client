package fr.insalyon.creatis.vip.cli.control;

import fr.insalyon.creatis.vip.cli.action.GetExecutionAction;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;

public class GetExecutionControl {
	Arguments args;
	DefaultApi api;
	public GetExecutionControl(DefaultApi api,Arguments args) {
		this.args=args;
		this.api=api;
	}
	
	public Execution execute () {
		String id=args.getListArgs().get("");
		GetExecutionAction getExecutionAction = new GetExecutionAction(id);
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

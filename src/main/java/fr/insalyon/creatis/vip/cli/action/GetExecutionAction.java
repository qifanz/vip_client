package fr.insalyon.creatis.vip.cli.action;

import fr.insalyon.creatis.vip.cli.control.Arguments;
import fr.insalyon.creatis.vip.cli.model.InfoExecutionDAO;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;

public class GetExecutionAction implements Action<Execution> {
	String executionId;
	Arguments args;
	DefaultApi api;

	public GetExecutionAction(DefaultApi api,Arguments args) {
		this.args=args;
		this.api=api;
		setExecutionId();
	}
	public void setExecutionId(){
		String id=args.getListArgs().get("");
		if (id!=null) {
			executionId=id;
		} else {
			InfoExecutionDAO infoDao=new InfoExecutionDAO();
			executionId=infoDao.getLastExecution().getExecutionIdentifier();
		}
	}

	
	public Execution execute() throws ApiException{
		return api.getExecution(executionId);
		
	}
}

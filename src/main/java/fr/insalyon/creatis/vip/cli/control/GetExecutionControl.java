package fr.insalyon.creatis.vip.cli.control;

import static java.lang.System.exit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.insalyon.creatis.vip.cli.action.GetExecutionAction;
import fr.insalyon.creatis.vip.cli.model.InfoExecutionDAO;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;

public class GetExecutionControl {
	 private static Logger log = LoggerFactory.getLogger(GetExecutionControl.class);  
	Arguments args;
	DefaultApi api;
	public GetExecutionControl(DefaultApi api,Arguments args) {
		this.args=args;
		this.api=api;
	}
	
	public Execution execute () {
		Execution execution = null;
		GetExecutionAction getExecutionAction=null;
		String id=args.getListArgs().get("");
		if (id!=null) {
			getExecutionAction = new GetExecutionAction(id);
		} else {
			InfoExecutionDAO infoDao=new InfoExecutionDAO();
			getExecutionAction=new GetExecutionAction(infoDao.getLastExecution().getExecutionIdentifier());
		}
		try {
			execution = getExecutionAction.execute(api);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("",e);
			System.err.println("Exception："+e.getMessage());
			exit(0);
		}
		InfoExecutionDAO infoDAO = new InfoExecutionDAO();
		infoDAO.upadteStatusByExecutionId(execution.getIdentifier(), execution.getStatus().toString());
		return execution;
	
	}
}

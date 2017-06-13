package fr.insalyon.creatis.vip.cli.control;

import java.util.List;

import fr.insalyon.creatis.vip.cli.action.GetResultAction;
import fr.insalyon.creatis.vip.cli.model.InfoExecutionDAO;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

import static java.lang.System.exit;

public class GetResultControl {
	Arguments args;
	DefaultApi api;
	public GetResultControl(DefaultApi api, Arguments args) {
		this.args=args;
		this.api=api;
	}
	
	public String execute(){
		GetResultAction getResultAction;
		String id=args.getListArgs().get("");
		if (id!=null) {
			getResultAction = new GetResultAction(id);
		} else {
			InfoExecutionDAO infoDao = new InfoExecutionDAO();
			getResultAction = new GetResultAction(infoDao.getLastExecution().getExecutionIdentifier());
		}
		try {
			List<String> filename= getResultAction.execute(api);
			return filename.get(0);
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exit(0);
			return null;
		}
	}
}

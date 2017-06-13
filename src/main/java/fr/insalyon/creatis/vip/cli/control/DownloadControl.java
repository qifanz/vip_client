package fr.insalyon.creatis.vip.cli.control;

import java.util.List;
import java.util.Map;

import fr.insalyon.creatis.vip.cli.action.DownloadAction;
import fr.insalyon.creatis.vip.cli.action.GetExecutionAction;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

public class DownloadControl {
	Arguments args;
	DefaultApi api;
	public DownloadControl (DefaultApi api,Arguments args) {
		this.args=args;
		this.api=api;
	}
	
	public void execute(){
		String id=args.getListArgs().get("");
		DownloadAction downloadAction=new DownloadAction(id);
		try {
			List<String> filename=downloadAction.execute(api);
			for(String s:filename){
				System.out.println(s);
			}
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

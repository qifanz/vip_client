package fr.insalyon.creatis.vip.cli.control;

import java.util.HashMap;
import java.util.Map;
import fr.insalyon.creatis.vip.cli.action.InitAndExecuteAction;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;

public class InitAndExecuteControl {
	

	private String[] args;
	String repertoire;
	
	
	public InitAndExecuteControl (String[]args,String repertoire)
	{
		this.args=args;
		this.repertoire=repertoire;
	}
	
	
	public Execution execute (DefaultApi api) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (int i = 2; i < args.length; i += 2) {
			parameters.put(args[i].substring(2), args[i + 1]);
		}
		parameters.put("results-directory", repertoire);	
		InitAndExecuteAction action = new InitAndExecuteAction("vip-cli", args[1], parameters);
		Execution execution=null;
		try {
			execution = action.execute(api);
			//UtilIO.printResultatExecute((Execution) execution,repertoire);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return execution;
	}
}

package fr.insalyon.creatis.vip.cli.control;

import java.text.SimpleDateFormat;
import static java.lang.System.exit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.insalyon.creatis.vip.cli.action.InitAndExecuteAction;
import fr.insalyon.creatis.vip.cli.model.InfoExecution;
import fr.insalyon.creatis.vip.cli.model.InfoExecutionDAO;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;

public class InitAndExecuteControl {
	 private static Logger log = LoggerFactory.getLogger(InitAndExecuteControl.class);  
	private DefaultApi api;
	private Arguments args;
	private String repertoire;
	
	public InitAndExecuteControl(DefaultApi api, Arguments args) {
		this.api = api;
		this.args = args;
	}

	public String getRepertoire() {
		return repertoire;
	}

	public Execution execute() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		repertoire = "/vip/Home/" + df.format(new Date());
		parameters.put("results-directory", repertoire);
		String id = "";
		for (Map.Entry<String, String> entry : args.getListArgs().entrySet()) {
			if (entry.getKey().equals("")) {
				id = entry.getValue();
			} else {
				parameters.put(entry.getKey(), entry.getValue());
			}
		}
		InitAndExecuteAction action = new InitAndExecuteAction("vip-cli", id, parameters);
		Execution execution = null;
		try {
			execution = action.execute(api);

		} catch (ApiException e) {
			
		//	e.printStackTrace();
			log.error("",e);
			System.err.println("Exception："+e.getMessage());
			exit(0);
		}
		InfoExecution infoExecution = new InfoExecution(execution.getIdentifier(), execution.getPipelineIdentifier(),
				execution.getStatus().toString(), repertoire, execution.getStartDate());
		InfoExecutionDAO infoDAO = new InfoExecutionDAO();
		infoDAO.persist(infoExecution);
		return execution;
	}
	
}

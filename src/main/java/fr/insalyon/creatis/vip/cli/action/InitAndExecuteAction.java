package fr.insalyon.creatis.vip.cli.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.System.exit;

import fr.insalyon.creatis.vip.cli.control.Arguments;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;
import fr.insalyon.creatis.vip.java_client.model.Pipeline;
import fr.insalyon.creatis.vip.java_client.model.PipelineParameter;

public class InitAndExecuteAction implements Action<Execution> {

	private Execution execution;
	private DefaultApi api;
	private Arguments args;
	private String repertoire;

	public InitAndExecuteAction(DefaultApi api, Arguments args) {
		this.api = api;
		this.args = args;
		setExecution();
	}
	
	public void setExecution(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		repertoire = "/vip/Home/" + df.format(new Date());
		parameters.put("results-directory", repertoire);
		String pipelineIdentifier = "";
		for (Map.Entry<String, String> entry : args.getListArgs().entrySet()) {
			if (entry.getKey().equals("")) {
				pipelineIdentifier = entry.getValue();
			} else {
				parameters.put(entry.getKey(), entry.getValue());
			}
		}
		execution=new Execution();
		execution.setName("vip-cli");
		execution.setPipelineIdentifier(pipelineIdentifier);
		execution.setInputValues(parameters);
	}

	public String getRepertoire (){
		return repertoire;
	}
	
	
	public Execution execute () throws ApiException {
		Pipeline pipelineToUse=api.getPipeline(execution.getPipelineIdentifier().replaceAll("/","%2F"));
		List<PipelineParameter> parametersToUse=pipelineToUse.getParameters();
		for (PipelineParameter pipelineParameter : parametersToUse) {
			if (execution.getInputValues().get(pipelineParameter.getName())==null) {
				System.err.println("not enough inputs or input not correct");
				exit(0);
			}
		}
		return api.initAndStartExecution(execution);
	}
}

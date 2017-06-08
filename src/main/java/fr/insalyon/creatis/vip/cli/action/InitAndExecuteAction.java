package fr.insalyon.creatis.vip.cli.action;

import java.util.List;
import java.util.Map;
import static java.lang.System.exit;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Execution;
import fr.insalyon.creatis.vip.java_client.model.Pipeline;
import fr.insalyon.creatis.vip.java_client.model.PipelineParameter;

public class InitAndExecuteAction implements Action<Execution> {

	Execution execution;
	
	public InitAndExecuteAction(String executionName ,String pipelineIdentifier, Map<String,Object>parameters){
		execution=new Execution();
		execution.setName(executionName);
		execution.setPipelineIdentifier(pipelineIdentifier);
		execution.setInputValues(parameters);
	}
	
	
	
	public Execution execute (DefaultApi api) throws ApiException {
		Pipeline pipelineToUse=api.getPipeline(execution.getPipelineIdentifier().replaceAll("/","%2F"));
		List<PipelineParameter> parametersToUse=pipelineToUse.getParameters();
		for (PipelineParameter pipelineParameter : parametersToUse) {
			if (execution.getInputValues().get(pipelineParameter.getName())==null) {
				System.err.println("not enough inputs");
				exit(0);
			}
		}
		return api.initAndStartExecution(execution);
	}
}

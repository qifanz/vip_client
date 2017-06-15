/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.action;

import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Pipeline;
import java.util.List;

/**
 *
 * @author qzhang
 */
public class GetPipelineAction implements Action<Pipeline>{
    private String pipelineName;
    private String pipelineIdentifier;
    private DefaultApi api;
    

    public GetPipelineAction(String name) {
    	 pipelineName=name;
    }

    
    public Pipeline execute() throws ApiException{
        List<Pipeline> listPipelines=api.listPipelines("");
        for (Pipeline pipeline:listPipelines) {
            if (pipeline.getName().equals(pipelineName)) {
                pipelineIdentifier=pipeline.getIdentifier().replaceAll("/" ,"%2F");
            }
        }
       
        return api.getPipeline(pipelineIdentifier);
    }
    
}

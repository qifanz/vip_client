/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.Action;

import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Pipeline;
import java.util.List;

/**
 *
 * @author qzhang
 */
public class GetPipelineAction extends Action{
    String pipelineName;
    String pipelineIdentifier;
    
    public GetPipelineAction() {
    }

    public GetPipelineAction(DefaultApi api) {
        super(api);
    }
    
    public void setPipelineName(String name){
        pipelineName=name;
    }
    
    public Object execute() throws ApiException{
        List<Pipeline> listPipelines=defaultApi.listPipelines("");
        for (Pipeline pipeline:listPipelines) {
            if (pipeline.getName().equals(pipelineName)) {
                int position=pipeline.getIdentifier().indexOf("/");
                pipelineIdentifier=pipeline.getIdentifier().substring(0, position);
                pipelineIdentifier+="%2F";
                pipelineIdentifier+=pipeline.getIdentifier().substring(position+1);
            }
        }
       
        return defaultApi.getPipeline(pipelineIdentifier);
    }
    
}

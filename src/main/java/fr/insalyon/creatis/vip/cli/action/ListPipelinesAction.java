/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.action;

import java.util.List;

import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.Pipeline;

/**
 *
 * @author qzhang
 */
public class ListPipelinesAction implements Action<List<Pipeline>>{

    public ListPipelinesAction(){
       
    }
    @Override
    public List<Pipeline> execute (DefaultApi api) throws ApiException{  
           return api.listPipelines("");
    }
    
    
}

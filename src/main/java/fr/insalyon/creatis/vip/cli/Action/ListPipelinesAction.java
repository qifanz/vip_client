/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.Action;

import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

/**
 *
 * @author qzhang
 */
public class ListPipelinesAction extends Action{
     public ListPipelinesAction() {
       
    }
    public ListPipelinesAction(DefaultApi api){
        super(api);
    }
    @Override
    public Object execute () throws ApiException{  
            System.out.println("--------------------------");
            result=this.defaultApi.listPipelines("");
            return result;
    }
    
    
}

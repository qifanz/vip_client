package fr.insalyon.creatis.vip.cli.Action;


import fr.insalyon.creatis.vip.java_client.ApiClient;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.PlatformProperties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author qzhang
 */
public class GetPlatformPropertyAction extends Action{
   
  
    
    public GetPlatformPropertyAction() {
       
    }
    public GetPlatformPropertyAction(DefaultApi api){
        super(api);
    }
    
    @Override
    public Object execute () throws ApiException{  
            System.out.println("--------------------------");
            result=this.defaultApi.getPlatformProperties();
            return result;
    }
    

}

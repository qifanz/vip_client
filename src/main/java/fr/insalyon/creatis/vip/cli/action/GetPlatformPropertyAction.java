package fr.insalyon.creatis.vip.cli.action;


import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;
import fr.insalyon.creatis.vip.java_client.model.PlatformProperties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author qzhang
 */
public class GetPlatformPropertyAction implements Action<PlatformProperties>{
   
  
    
    public GetPlatformPropertyAction(){
        
    }
    
  

	@Override
	public PlatformProperties execute(DefaultApi api) throws ApiException {
		PlatformProperties result=api.getPlatformProperties();
        return result;
	}
    

}

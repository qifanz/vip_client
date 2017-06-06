/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.Action;

import fr.insalyon.creatis.vip.java_client.ApiClient;
import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

/**
 *
 * @author qzhang
 */
public abstract class Action {
    
    protected DefaultApi defaultApi;
    protected Object result;
    
    public Action() {
    }
    public Action (DefaultApi api) {
        this.defaultApi=api;
    }
    
    public Object execute () throws ApiException{
        return result;
    }
    
    public void SetDefaultApi (DefaultApi api) {
        this.defaultApi=api;
    }
    public DefaultApi getDefaultApi() {
        return defaultApi;
    }
    public Object getResult() {
        return result;
    }
}

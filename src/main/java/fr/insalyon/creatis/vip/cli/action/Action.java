/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.cli.action;

import fr.insalyon.creatis.vip.java_client.ApiException;
import fr.insalyon.creatis.vip.java_client.api.DefaultApi;

/**
 *
 * @author qzhang
 */
public interface  Action<T> {
    

    
    public T execute (DefaultApi api) throws ApiException;
    

}

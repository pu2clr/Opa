/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.opaopa;


import java.util.List;


/**
 *
 * @author rcaratti
 */
public class Services {

    // LinkedHashMap<String, Integer> services;
	List<Service> services;

    Services() {

        services = OpaServer.getTopServices();
        services.addAll(OpaServer.getAllServices());

    }
    
    
    public List<Service> getServices() {
    	return services;
    }

    
}

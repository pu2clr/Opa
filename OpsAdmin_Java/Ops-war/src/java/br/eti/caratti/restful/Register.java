/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.caratti.restful;

import br.eti.caratti.ops.ejb.OpaEJB;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author rcaratti
 */
@Stateless
@Path("br.eti.caratti.ops")
public class Register {

    @EJB OpaEJB opaEJB;
    
    
    @Context
    private UriInfo context;
    

    /** Creates a new instance of Register */
    public Register() {
    }
    
    
    /**
     * Retrieves representation of an instance of br.eti.caratti.restful.Register
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("/{deviceId}/{latitude}/{longitude}/{messageType}/{message}")
    public String getJson(@PathParam("deviceId")    String deviceId,
                          @PathParam("latitude")    String latitude,
                          @PathParam("longitude")   String longitude,
                          @PathParam("messageType") Integer messageType,
                          @PathParam("message")     String message) {
        
        return opaEJB.sendOpa(deviceId,latitude,longitude,messageType,message);
     }

    /**
     * PUT method for updating or creating an instance of Register
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes({"application/json", "application/xml"})
    @Path("/{deviceId}/{latitude}/{longitude}/{messageType}/{message}")
    public void putJson(        
            @PathParam("deviceId") String deviceId,
            @PathParam("latitude") String latitude,
            @PathParam("longitude") String longitude,
            @PathParam("messageType") int messageType,
            @PathParam("message") String message) {


        // return r;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.StringReader;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;

import javax.ws.rs.core.MediaType;


/**
 * REST Web Service
 *
 * @author Brad Ryan <brad.m.ryan@gmail.com>
 */
@Path("messages")
@ApplicationScoped
public class MessagesService {
    private Messages messages = new Messages();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getAll() {
        System.out.println("GET METHOD");
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (String message : messages.getMessages()) {
            System.out.println(message);
            json.add(message);
        }
        return json.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void add(String str) {
        System.out.println("POST METHOD");
        System.out.println(str);
        //JsonObject json = Json.createReader(new StringReader(str)).readObject();
        // Expects { "item": "some todoList entry" }      
        messages.add(str);
        
    }
}

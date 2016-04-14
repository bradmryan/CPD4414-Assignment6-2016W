/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.StringReader;
import java.sql.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.core.MediaType;
import model.Message;


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
        for (Message message : messages.getMessages()) {
            System.out.println(message);
            json.add(message.toJSON());
        }
        return json.build();
    }
    
    @GET
    @Path("{id}")
    @PathParam("id")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getById(){
        return messages.getMessageById(id);
    }
    
    @GET
    @Path("{from}/{to}")
    @PathParam("from")
    @PathParam("to")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getByDate(){
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Message message : messages.getMessages()) {
            if (message.getSentTime() > from && message.getSentTime() < to) {
                json.add(message.toJSON());
            }
        }
        return json.build();
    }
    

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void add(String str) {
        
        JsonObject json = Json.createReader(new StringReader(str)).readObject();
        Message message = new Message(
                json.getInt("id"),
                json.getString("title"),
                json.getString("content"),
                json.getString("author"),
                Date.valueOf(json.getString("sentTime"))
        );

        messages.add(message);
        
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(){
        messages.edit(id, message);
    }
    
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void destroy(){
        messages.destroy(id);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import model.Message;

/**
 *
 * @author Brad Ryan <brad.m.ryan@gmail.com>
 */
@ServerEndpoint("/messages")
public class MessagesServer {
    
    @Inject
    private Messages messages;
    
    @OnMessage
    public void onMessage(String str, Session session) throws IOException{
        
        JsonObject json = Json.createReader(new StringReader(str)).readObject();
        
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        
        if (json.containsKey("getAll")) {
            JsonArrayBuilder jsonArray = Json.createArrayBuilder();
            for (Message message : messages.getMessages()) {
                jsonArray.add(message.toJSON());
            }
            basic.sendText(jsonArray.build().toString());
        } else if (json.containsKey("getById")) {
            int id = json.getInt("getById");
            
            basic.sendText(messages.getMessageById(id).toJSON().toString());
        } else if (json.containsKey("getFromTo")) {
            JsonArrayBuilder jsonArray = Json.createArrayBuilder();
            
            Date from = new Date( json.getJsonArray("getFromTo").getJsonString(0).toString());
            Date to = new Date ( json.getJsonArray("getFromTo").getJsonString(1).toString());
            
            for (Message message : messages.getMessages()) {
                if (message.getSentTime().after(from) && message.getSentTime().before(to)) {
                    jsonArray.add(message.toJSON());
                }
            }
            basic.sendText(jsonArray.build().toString());
            
        } else if (json.containsKey("post")) {
            JsonObject js = Json.createReader(new StringReader(json.getString("post"))).readObject();
            Message message = new Message(
                json.getInt("id"),
                json.getString("title"),
                json.getString("content"),
                json.getString("author"),
                java.sql.Date.valueOf(json.getString("sentTime"))
            );

            messages.add(message);
            basic.sendText(message.toString());
        } else if (json.containsKey("put")) {
            JsonObject js = Json.createReader(new StringReader(json.getString("put"))).readObject();
            Message message = new Message(
                json.getInt("id"),
                json.getString("title"),
                json.getString("content"),
                json.getString("author"),
                java.sql.Date.valueOf(json.getString("sentTime"))
            );
            messages.edit(json.getInt("id"), message);
        } else if (json.containsKey("delete")) {
            messages.destroy(json.getInt("delete"));
        }
        
    }
    
    @OnOpen
    public void onOpen(Session session){
        try {
            RemoteEndpoint.Basic basic = session.getBasicRemote();
            
            JsonArrayBuilder jsonArray = Json.createArrayBuilder();
            
            for (Message message : messages.getMessages()) {
                if (message.getSentTime().after(new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000)))) {
                    jsonArray.add(message.toJSON());
                }
            }
            basic.sendText(jsonArray.build().toString());
        } catch (IOException ex) {
            Logger.getLogger(MessagesServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

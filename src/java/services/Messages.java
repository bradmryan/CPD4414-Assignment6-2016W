/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import model.Message;


/**
 *
 * @author Brad Ryan <brad.m.ryan@gmail.com>
 */
@ApplicationScoped
public class Messages {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }
    
    public Message getMessageById(int id){
        for(Message message : messages){
            if (message.getId() == id) return message;
        }
        return null;
    }
    
    public List<Message> getMessagesByDate(Date from, Date to){
        List<Message> messagesByDate = new ArrayList();
        for(Message message : messages){
            if (message.getSentTime().after(from) && message.getSentTime().before(from)) messagesByDate.add(message);
        }
        return messagesByDate;
    }
    
    public void add(Message content){
        messages.add(content);
    }
    
    public void edit(int id, Message message){
        messages.set(id, message);
    }
    
    public void destroy(int id){
        messages.set(id, null);
    }
    
}

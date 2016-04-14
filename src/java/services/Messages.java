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
        return messages.get(id);
    }
    
    public List<Message> getMessageByDate(Date from, Date to){
        return messages;
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

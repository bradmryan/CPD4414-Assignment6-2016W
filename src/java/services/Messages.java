/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;


/**
 *
 * @author Brad Ryan <brad.m.ryan@gmail.com>
 */
@ApplicationScoped
public class Messages {
    private List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }
    
    public void add(String content){
        messages.add(content);
        
    }
    
    
}

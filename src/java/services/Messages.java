/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import model.Message;


/**
 *
 * @author Brad Ryan <brad.m.ryan@gmail.com>
 */
@ApplicationScoped
public class Messages {
    private List<Message> messages;

    public Messages() {
        getMessagesFromDB();
    }

    
    
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
    
    public void add(Message message){
        addMessageToDB(message);
        getMessagesFromDB();
    }
    
    public void edit(int id, Message message){
        updateMessageFromDB( new Message(
                id,
                message.getTitle(),
                message.getContent(),
                message.getAuthor(),
                message.getSentTime()
        ));
        getMessagesFromDB();
        
    }
    
    public void destroy(int id){
        deleteMessageFromDB(id);
        getMessagesFromDB();
    }
    
    //Database Methods
    private void getMessagesFromDB(){
        messages = new ArrayList();
        try (Connection conn = utilities.DB.getConnection()) {
            String sql = "SELECT * FROM messages";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                messages.add( new Message(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("author"),
                        rs.getTimestamp("sentTime")
                ));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addMessageToDB(Message message){
        try (Connection conn = utilities.DB.getConnection()) {
            String sql = "INSERT INTO messages VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message.getId());
            ps.setString(2, message.getTitle());
            ps.setString(3, message.getContent());
            ps.setString(4, message.getAuthor());
            ps.setDate(5, new java.sql.Date(message.getSentTime().getTime()));
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateMessageFromDB(Message message){
        try (Connection conn = utilities.DB.getConnection()) {
            String sql = "UPDATE messages SET title=?, content=?, author=?, sentDate=? WHERE id=?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, message.getTitle());
            ps.setString(2, message.getContent());
            ps.setString(3, message.getAuthor());
            ps.setDate(4, new java.sql.Date(message.getSentTime().getTime()));
            ps.setInt(5, message.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void deleteMessageFromDB(int id){
        try (Connection conn = utilities.DB.getConnection()) {
            String sql = "DELETE FROM messages WHERE id=?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

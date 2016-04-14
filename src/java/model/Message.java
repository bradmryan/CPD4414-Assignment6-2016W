/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.StringWriter;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;

/**
 *
 * @author Brad Ryan <brad.m.ryan@gmail.com>
 */
public class Message {
    private int id;
    private String title;
    private String content;
    private String author;
    private Date sentTime;

    public Message() {
    }

    public Message(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.sentTime = new Date();
    }
    
    public Message(int id, String title, String content, String author, Date sentTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.sentTime = sentTime;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }
    
    public JsonObject toJSON(){
        StringWriter out = new StringWriter();
        JsonGeneratorFactory factory = Json.createGeneratorFactory(null);
        JsonGenerator gen = factory.createGenerator(out);
        gen.writeStartObject()
                .write("id", id)
                .write("title", title)
                .write("content", content)
                .write("author", author)
                .write("senttime", sentTime.toString())
              .writeEnd();
        gen.close();
        return (JsonObject) out;
    }
    
}

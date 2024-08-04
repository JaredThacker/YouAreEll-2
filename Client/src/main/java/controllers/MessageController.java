package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Id;
import models.Message;

public class MessageController {
    ServerController sc;
    private ObjectMapper objectMapper = new ObjectMapper();


    private HashSet<Message> messagesSeen;
    // why a HashSet??

    public MessageController(ServerController shared) {
        sc = shared;
        messagesSeen = new HashSet<Message>();
    }

    public ArrayList<Message> getMessages() {
       String jsonInput = sc.sendRequest("/messages", "GET", "");
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();
        List<Message> msgs;
        try {
            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));

            ArrayList<Message> msgList = new ArrayList<>(msgs);
            // return array of Ids
            return msgList;
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Message> getMessagesForId(String Id) {
        String jsonInput = sc.sendRequest("/ids/" + Id + "/messages", "GET", "");
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();
        List<Message> msgs;
        try {
            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));

            ArrayList<Message> msgList = new ArrayList<>(msgs);
            // return array of Ids
            return msgList;
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }

    public Message getMessageForSequence(String seq) {
        return null;
    }

    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        return null;
    }

    public Message postMessage(String myId, String toId, Message msg) {
        try {
            msg.setFromid(myId);
            msg.setToid(toId);
            sc.sendRequest("/ids/" + msg.getFromid() + "/messages", "POST", objectMapper.writeValueAsString(msg));
            return msg;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
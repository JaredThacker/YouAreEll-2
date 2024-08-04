package controllers;

import models.Id;
import models.Message;

import java.util.List;

public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085";
    private MessageController msgCtrl;
    private IdController idCtrl;

    public TransactionController(MessageController m, IdController j) {
        msgCtrl = m;
        idCtrl = j;
    }

    public List<Id> getIds() {
        return idCtrl.getIds();
    }

    public Id getId(String id) {
        return idCtrl.getId(id);
    }

    public String putId(String existingUid, String newGithubName, String newUsername) {
        Id tid = new Id(existingUid, newUsername, newGithubName);
        idCtrl.putId(tid);
        return String.format("Id %s updated", existingUid);
    }

    public Id deleteId(Id id) {
        return idCtrl.deleteId(id);
    }

    public String postId(String idToRegister, String githubName) {
         Id tid = new Id(idToRegister, githubName);
         idCtrl.postId(tid);
         return String.format("Id %s registered", idToRegister);
    }

    public String postId(String userId, String username, String githubName) {
        Id tid = new Id(userId, username, githubName);
        idCtrl.postId(tid);
        return "Id registered.";
    }

    public List<Message> getMessages() {
        return msgCtrl.getMessages();
    }

    public String postMessage(String idFrom, String idTo, String message){
        Message m = new Message(message, idFrom, idTo);
        msgCtrl.postMessage(idFrom, idTo, m);
        return "Message was posted";
    }
}

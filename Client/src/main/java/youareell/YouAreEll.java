package youareell;

import java.util.List;

import controllers.*;
import models.Id;
import models.Message;

public class YouAreEll {
    private TransactionController tt;

    public YouAreEll (TransactionController t) {
        this.tt = t;
    }

    public static void main(String[] args) {
        // hmm: is this Dependency Injection?
        YouAreEll urlhandler = new YouAreEll(
            new TransactionController(
                new MessageController(ServerController.shared()), 
                new IdController(ServerController.shared())
        ));
    }

    public String get_ids() {
        List<Id> allIds = tt.getIds();
        StringBuilder sb = new StringBuilder();
        for (models.Id id : allIds) {
            sb.append(id.toString()+"\n");
        }
        return sb.toString();
    }

    public boolean doesIdExist(String uid) {
        List<Id> allIds = tt.getIds();
        return allIds.stream().filter(e -> e.getUserid().equals(uid)).count() == 1;
    }

    public String postId(String userId, String githubName, String name) {
        String createdId = tt.postId(userId, name, githubName);
        return createdId;
    }

    public String putId(String userId, String githubName, String name) {
        String updatedId = tt.putId(userId, githubName, name);
        return updatedId;
    }

    public String get_messages() {
        List<models.Message> latestMessages = tt.getMessages();
        StringBuilder sb = new StringBuilder();
        for (models.Message msg : latestMessages) {
            sb.append(msg.toString()+"\n");
        }
        return sb.toString();
    }

    public String postMessage(String from, String to, String body){
        String post = tt.postMessage(from,to,body);
        return post;
    }


}

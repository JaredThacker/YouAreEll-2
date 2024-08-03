package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Id;

public class IdController {
    ServerController sc;
    private HashMap<String, Id> allIds;
    private ObjectMapper objectMapper;

    Id myId;

    public IdController(ServerController shared) {
        sc = shared;
        allIds = new HashMap<String, Id>();
        objectMapper = new ObjectMapper();
    }

    public ArrayList<Id> getIds() {
        String jsonInput = sc.sendRequest("/ids", "GET", "");
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();
        List<Id> ids;
        try {
            ids = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Id.class));

            ArrayList<Id> idList = new ArrayList<>(ids);
            // return array of Ids
            return idList;
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }

    public Id getId(String id) {
        try {
            return getIds().stream().filter(e -> e.getUserid().equals(id)).findFirst().orElse(null);
        } catch (Exception exception) {
            System.out.println("Sent invalid id");
            return null;
        }
    }

    public Id putId(Id id) {
        try {
            return objectMapper.readValue(sc.sendRequest("/ids", "PUT", objectMapper.writeValueAsString(id)), Id.class);
        } catch (JsonProcessingException exception) {
            System.out.println(String.format("Id is invalid %s", exception.getMessage()));
            return null;
        }
    }

    public Id postId(Id id) {
        try {
            return objectMapper.readValue(sc.sendRequest("/ids", "POST", objectMapper.writeValueAsString(id)), Id.class);
        } catch (JsonProcessingException exception) {
            System.out.println("Id is invalid");
            return null;
        }
    }

    public Id deleteId(Id id) {
        try {
            return objectMapper.readValue(sc.sendRequest("/ids", "DELETE", objectMapper.writeValueAsString(id)), Id.class);
        } catch (JsonProcessingException exception) {
            System.out.println("Id is invalid");
            return null;
        }
    }
 
}
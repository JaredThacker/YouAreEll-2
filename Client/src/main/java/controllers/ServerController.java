package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Id;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

//this class calls the server and returns the string to the calling controller

public class ServerController {
    private final String rootURL = "http://zipcode.rocks:8085";

    private static final ServerController svr = new ServerController();

    private ServerController() {
    }

    public static ServerController shared() {
        return svr;
    }

    /**
     * Sends a GET or POST request to the specified URL.
     *
     * @param resource      The URL to send the request to
     * @param method   Either "GET" or "POST"
     * @param body     The body of the request, if any
     * @return         A string containing the response from the server
     */
    public String sendRequest(String resource, String method, String body) {
        try {
            String url = rootURL + resource;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            

            if (method.equals("POST") || method.equals("PUT")) {
                // Send POST request using the body parameter
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(body);
                wr.flush();
                wr.close();
            }

            int responseCode = con.getResponseCode();

            BufferedReader in;
            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return content.toString();
        } catch (IOException e) {
            System.out.println("Error sending request: " + e.getMessage());
            return "";
        }
    }
}

// ServerController.shared.doGet()
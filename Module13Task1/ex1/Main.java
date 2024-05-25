package ua.goit.polymorpism.Module13.Homework.Module13Task1.ex1;

import com.google.gson.Gson;
import ua.goit.polymorpism.Module13.Homework.Module13Task1.User;


import java.io.*;
import java.net.*;
import java.net.http.*;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users"))
                .header("Content-Type", "application/json; utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(send.body());
    }
}



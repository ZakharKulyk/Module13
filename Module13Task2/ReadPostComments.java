package ua.goit.polymorpism.Module13.Homework.Module13Task2;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ReadPostComments {
    public static  void main(String[] args) throws IOException, InterruptedException {
        getCommentsFromPostsOfUser(1);


    }
    public static   void getCommentsFromPostsOfUser( int userId){
        HttpRequest request =HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/"+Integer.toString(userId)+"/posts"))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        List<Post> posts = Arrays.asList(gson.fromJson(response.body(), Post[].class));

        Map<Post,List<Comment>>map = new HashMap<>();
        int i =1;
        for(Post post : posts){

            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/"+i+"/comments"))
                    .GET()
                    .build();

            HttpClient client1 = HttpClient.newHttpClient();
            HttpResponse<String> response1 = null;
            try {
                response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            List<Comment>comments = Arrays.asList(gson.fromJson(response1.body(), Comment[].class));
            map.put(post,comments);

            i++;
        }

        for (Map.Entry<Post, List<Comment>> entry : map.entrySet()) {
            Post post = entry.getKey();
            List<Comment> comments = entry.getValue();
            String fileName = "user-"+Integer.toString(userId)+"-post-" + post.getId() + "-comments.json";

            File file = new File(fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try (FileWriter fileWriter = new FileWriter(file)) {
                gson.toJson(comments, fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}


class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

class Comment {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
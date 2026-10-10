
package com.pipeline.service;

import org.w3c.dom.ls.LSOutput;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Service {

    public static void testApi() throws Exception {

        String apiKey = System.getenv("GROQ_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("GROQ_API_KEY is missing!");
        }
//        String json = """
//                {
//                  "model": "openai/gpt-oss-20b",
//                  "messages": [
//                    {
//                      "role": "user",
//                      "content": "Reply with exactly: AI API connected successfully!"
//                    }
//                  ]
//                }
//                """;
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(
//                        "https://api.groq.com/openai/v1/chat/completions"
//                ))
//                .header("Authorization", "Bearer " + apiKey)
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(json))
//                .build();
//
//        HttpClient client = HttpClient.newHttpClient();
//
//        HttpResponse<String> response = client.send(
//                request,
//                HttpResponse.BodyHandlers.ofString()
//        );
//
//        System.out.println("Status: " + response.statusCode());
//        System.out.println("Response: " + response.body());
    }
}

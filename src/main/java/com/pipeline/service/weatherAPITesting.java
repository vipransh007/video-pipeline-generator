package com.pipeline.service;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.net.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class weatherAPITesting {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Welcome to the weather API!");
        System.out.print("Please enter the city : ");
        Scanner sc = new Scanner(System.in);
        String city = sc.nextLine();
//        String city = "Pune";

        // Step 1: Convert city name to coordinates
        String geoUrl = "https://geocoding-api.open-meteo.com/v1/search"
                + "?name=" + city
                + "&count=1&language=en&format=json";


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(geoUrl))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        JsonNode results = root.get("results");

        double longitude =  0.0;
        double latitude = 0.0;
        if(results != null && !results.isEmpty()) {

             longitude = results.get(0).get("longitude").asDouble();
             latitude = results.get(0).get("latitude").asDouble();

        }
        else
            System.out.println("Not Found");

        String url =
                "https://api.open-meteo.com/v1/forecast"
                        + "?latitude=" +latitude
                        + "&longitude="+longitude
                        + "&current=temperature_2m,relative_humidity_2m,weather_code";

        HttpRequest weatherRequest = HttpRequest.newBuilder().
                uri(URI.create(url))
                        .GET()
                        .build();

        HttpClient weatherClient = HttpClient.newHttpClient();
        HttpResponse<String> weatherResponse = weatherClient.send(weatherRequest, HttpResponse.BodyHandlers.ofString());


        JsonNode weatherRoot = mapper.readTree(weatherResponse.body());

        JsonNode current = weatherRoot.get("current");

        System.out.println("Your City : " + city);
        System.out.println("Your Latitude : " + latitude);
        System.out.println("Your Longitude : " + longitude);
        System.out.println("Your City's Temperature : " + current.get("temperature_2m").asDouble());
        System.out.println("Your City's Humidity : " + current.get("relative_humidity_2m").asDouble());
    }
}

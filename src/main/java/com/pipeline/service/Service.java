package com.pipeline.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;

public class Service {

    public static void testApi() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // Read JSON file from the data directory
        JsonNode root = mapper.readTree(
                Path.of("scenes", "solar_system_scenes.json").toFile()
        );

        // Print the complete JSON structure
        System.out.println(root.toPrettyString());

        // Print video details
        System.out.println("Video: " + root.path("videoTitle").asText());
        System.out.println("Scenes: " + root.path("scenes").size());

        // Print each scene's frame range
        for (JsonNode scene : root.path("scenes")) {
            System.out.println(
                    scene.path("startFrame").asInt() + " - " +
                            scene.path("endFrame").asInt()
            );
        }
    }
}

//        String apiKey = System.getenv("GROQ_API_KEY");
//
//        if (apiKey == null || apiKey.isBlank()) {
//            throw new RuntimeException("GROQ_API_KEY is missing!");
//        }
//
//        String topic = "The Solar System";
//
//        String instructions = """
//                Generate a video scene plan about: %s
//
//                Return ONLY a valid JSON object.
//                No markdown, explanations, or code fences.
//
//                Follow this schema:
//                {
//                  "videoTitle": "string",
//                  "width": 1280,
//                  "height": 720,
//                  "fps": 30,
//                  "scenes": [
//                    {
//                      "startFrame": 1,
//                      "endFrame": 150,
//                      "background": "#10152F",
//                      "elements": [
//                        {
//                          "type": "text",
//                          "text": "Example",
//                          "x": 350,
//                          "y": 300,
//                          "color": "#FFFFFF",
//                          "fontSize": 60
//                        },
//                        {
//                          "type": "circle",
//                          "x": 640,
//                          "y": 450,
//                          "radius": 60,
//                          "color": "#FFD700"
//                        }
//                      ]
//                    }
//                  ]
//                }
//
//                Rules:
//                1. Generate exactly 5 scenes.
//                2. Each scene lasts 150 frames.
//                3. Start the first scene at frame 1.
//                4. Use sequential, non-overlapping frame ranges.
//                5. Only use element types "text" and "circle".
//                6. Text elements must have type, text, x, y,
//                   color, fontSize.
//                7. Circle elements must have type, x, y,
//                   radius, color.
//                8. Keep elements inside the 1280x720 canvas.
//                9. Use valid hexadecimal colors.
//                10. Use integers for coordinates, dimensions,
//                    font sizes, radii, and frame numbers.
//                11. Make scenes educational and visually distinct.
//                12. Use only the properties in the schema.
//                13. Generate actual content about the given topic.
//                14. Ensure the output is valid JSON.
//                """.formatted(topic);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        ObjectNode requestBody = mapper.createObjectNode();
//
//        requestBody.put("model", "openai/gpt-oss-20b");
//
////        requestBody.putObject("response_format")
////                .put("type", "json_object");
//
//        ArrayNode messages = requestBody.putArray("messages");
//
//        ObjectNode userMessage = messages.addObject();
//        userMessage.put("role", "user");
//        userMessage.put("content", instructions);
//
//        String json = mapper.writeValueAsString(requestBody);
//
//        System.out.println(json);
//
//
////        String json = """
////                {
////                  "model": "openai/gpt-oss-20b",
////                  "messages": [
////                    {
////                      "role": "user",
////                      "content": "Reply with exactly: AI API connected successfully!"
////                    }
////                  ]
////                }
////                """;
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
//        System.out.println("Sending request to " + request.uri());
//
//        HttpClient client = HttpClient.newHttpClient();
////
//        HttpResponse<String> response = client.send(
//                request,
//                HttpResponse.BodyHandlers.ofString()
//        );
//        System.out.println("Status: " + response.statusCode());
//
//        if (response.statusCode() < 200 ||
//                response.statusCode() >= 300) {
//            throw new RuntimeException("Groq API error: " + response.body());
//        }
//
//        JsonNode responseJson = mapper.readTree(response.body());
//
//        String sceneJson = responseJson
//                .path("choices")
//                .path(0)
//                .path("message")
//                .path("content")
//                .asText();
//
//        System.out.println("Generated Scene JSON:");
//        System.out.println(sceneJson);
//        JsonNode scenes = mapper.readTree(sceneJson);
//
//        Path outputPath = Path.of("output", "scenes.json");
//        Files.createDirectories(outputPath.getParent());
//
//        mapper.writerWithDefaultPrettyPrinter()
//                .writeValue(outputPath.toFile(), scenes);
//
//        System.out.println("Saved to: " + outputPath.toAbsolutePath());
//    }
//}

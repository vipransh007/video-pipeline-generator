package com.pipeline.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class YoutubeService {

    private static final String APPLICATION_NAME =
            "Java Video Pipeline";

    private static final String CREDENTIALS_FILE =
            "client_secret.json";

    private static final String TOKENS_DIRECTORY =
            "tokens";

    private static final List<String> SCOPES = List.of(
            "https://www.googleapis.com/auth/youtube.readonly"
    );

    public static void verifyLogin() throws Exception {

        System.out.println("Starting Google login verification...");

        // Initialize HTTP transport and JSON factory
        System.out.println("Initializing HTTP transport...");

        NetHttpTransport httpTransport =
                GoogleNetHttpTransport.newTrustedTransport();

        GsonFactory jsonFactory =
                GsonFactory.getDefaultInstance();

        System.out.println("HTTP transport initialized.");

        // Load OAuth client credentials
        System.out.println("Loading OAuth credentials...");

        GoogleClientSecrets clientSecrets;

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(CREDENTIALS_FILE),
                StandardCharsets.UTF_8)) {

            clientSecrets = GoogleClientSecrets.load(
                    jsonFactory,
                    reader
            );
        }

        System.out.println("OAuth credentials loaded.");

        // Configure OAuth authorization
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport,
                        jsonFactory,
                        clientSecrets,
                        SCOPES
                )
                        .setDataStoreFactory(
                                new FileDataStoreFactory(
                                        new File(TOKENS_DIRECTORY)
                                )
                        )
                        .setAccessType("offline")
                        .build();

        // Start local callback server
        LocalServerReceiver receiver =
                new LocalServerReceiver.Builder()
                        .setPort(8888)
                        .build();

        try {
            System.out.println(
                    "Waiting for Google authorization..."
            );

            // Sign in with Google
            Credential credential =
                    new AuthorizationCodeInstalledApp(
                            flow,
                            receiver
                    ).authorize("user");

            System.out.println(
                    "Google OAuth authorization successful!"
            );

            System.out.println(
                    "Access token received: " +
                            (credential.getAccessToken() != null)
            );

            // Connect to YouTube API
            System.out.println("Creating YouTube API client...");

            YouTube youtube = new YouTube.Builder(
                    httpTransport,
                    jsonFactory,
                    credential
            )
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            System.out.println("YouTube API client created.");

            // Fetch the authenticated user's channel
            System.out.println("Fetching YouTube channel...");

            YouTube.Channels.List request =
                    youtube.channels().list(
                            List.of("snippet")
                    );

            request.setMine(true);

            ChannelListResponse response =
                    request.execute();

            System.out.println("YouTube API response received.");

            if (response.getItems() == null ||
                    response.getItems().isEmpty()) {

                System.out.println(
                        "Login succeeded, but no YouTube channel was found."
                );

            } else {
                String channelName = response.getItems()
                        .get(0)
                        .getSnippet()
                        .getTitle();

                String channelId = response.getItems()
                        .get(0)
                        .getId();

                System.out.println(
                        "YouTube channel verified successfully!"
                );

                System.out.println(
                        "Channel name: " + channelName
                );

                System.out.println(
                        "Channel ID: " + channelId
                );

                System.out.println(
                        "No video was uploaded."
                );
            }

        } finally {
            receiver.stop();
            System.out.println("Authorization server stopped.");
        }
    }
}



//will be checking the code
package com.sunday.friends.foundation.controller;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.service.UserService;
import org.springframework.web.bind.annotation.RequestHeader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

public class TokenVerifier {

    public static boolean verify(@RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {

        // Extract variables
        String idTokenString = String.valueOf(headers.get("idToken"));
        String clientId = String.valueOf(headers.get("idClient"));
        String userId = String.valueOf(headers.get("idUser"));

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // Generate Token
        GoogleIdToken idToken = verifier.verify(idTokenString);

        // Check for Validation
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String emailToken = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String emailDb = getEmail(userId);

            if (emailVerified && emailToken.equals(emailDb))
                return true;
        }
        return false;

    }

    public static String getEmail(String userId) {
        Integer userIdInt = Integer.valueOf(userId);
        UserService userService = new UserService();
        Users user = userService.getUser(userIdInt);
        if(user == null)
            return "";
        return user.getEmail();
    }
}

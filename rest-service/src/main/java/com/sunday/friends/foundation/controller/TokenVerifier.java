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

/**
 * The Google Token Verifier
 * Doc Link: https://developers.google.com/identity/sign-in/web/backend-auth
 * @author Mahapatra Manas
 * @version 1.0
 * @since 11-20-2020
 */

public class TokenVerifier {

    public static boolean verify(@RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        return verify(headers, null);
    }

    public static boolean verify(@RequestHeader Map<String, String> headers, String inputEmail) throws GeneralSecurityException, IOException {
        // Extract variables
        String idTokenString = String.valueOf(headers.get("idtoken"));
        String clientId = String.valueOf(headers.get("idclient"));
        String emailId = String.valueOf(headers.get("idemail"));

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        // Generate Token
        GoogleIdToken idToken = verifier.verify(idTokenString);

        // Check for Validation
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String emailToken = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            if (emailVerified && emailToken.equals(emailId)) {
                if (inputEmail != null && !emailToken.equals(inputEmail))
                    return false;
                return true;
            }

        }
        return false;

    }
}

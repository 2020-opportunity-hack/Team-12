package com.sunday.friends.foundation.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.service.UserService;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
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
        String method = String.valueOf(headers.get("method"));
        if (null != method && !method.isEmpty() && "apple".equals(method)) {
            String idTokenString = String.valueOf(headers.get("idtoken"));
            String emailId = String.valueOf(headers.get("idemail"));
            String tokenEmail = decodeTokenParts(idTokenString);
            if (emailId.equals(tokenEmail)) {
                return true;
            }
            return false;
        } else {
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

    static String decodeTokenParts(String token)
    {
        String part = token.split("\\.", 0)[1];
        byte[] bytes = Base64.getUrlDecoder().decode(part);
        String decodedString = new String(bytes, StandardCharsets.UTF_8);
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Gson gson = new Gson();
        Map<String, String> myMap = gson.fromJson(decodedString, type);
        return myMap.get("email");
    }
}

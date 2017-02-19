package org.ivt.agregator.rest;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.ivt.agregator.dao.UserDao;
import org.ivt.agregator.entity.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

@Path("/auth")
public class AuthController {

    private static String CLIENT_ID = "4763853757-hgedrqim8lj2glrnn7gk66c6qreb56uc.apps.googleusercontent.com";

    private UserDao userDao;
    private HttpTransport httpTransport;
    private JsonFactory jsonFactory;
    private GoogleIdTokenVerifier verifier;

    public AuthController(UserDao userDao) {
        this.userDao = userDao;
        httpTransport = new NetHttpTransport();
        jsonFactory = new JacksonFactory();
        verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }

    @GET
    @Path("/google/validate")
    public String validateGoogleToken(@QueryParam("gtoken") String gtoken) {
        GoogleIdToken token = null;
        try {
            token = verifier.verify(gtoken);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        if (token != null) {
            return getAuthToken(token);
        }
        throw new IllegalArgumentException("Неверный token");
    }

    private String getAuthToken(GoogleIdToken token) {
        String innerToken = UUID.randomUUID().toString();
        GoogleIdToken.Payload payload = token.getPayload();
        User user = userDao.findByGoogleID(payload.getSubject());
        if (user == null) {
            createUser(payload, innerToken);
        } else {
            userDao.updateInnerToken(user, innerToken);
        }
        return innerToken;
    }

    private void createUser(GoogleIdToken.Payload payload, String innerToken) {
        User user = new User();
        user.setGoogleId(payload.getSubject());
        user.setToken(innerToken);
        user.setEmail(payload.getEmail());
        user.setImageUrl((String) payload.get("picture"));
        user.setLocale((String) payload.get("locale"));
        user.setFamilyName((String) payload.get("family_name"));
        user.setFirstName((String) payload.get("given_name"));
        userDao.save(user);
    }
}

package com.ishmael.spotit;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

public class Driver {
    public static void main(String[] args) {
        new Driver();
    }

    private final String clientID = System.getenv("CLIENT_ID");
    private final String clientSecret = System.getenv("CLIENT_SECRET");
    private final URI redirectURI = SpotifyHttpManager.makeUri("https://accounts.spotify.com/authorize");

    private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectURI)
            .build();

    private final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().build();

    public Driver() {
        authorizationCodeUri();
        System.out.println("Hello World");
    }

    public void authorizationCodeUri() {
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());
    }
}

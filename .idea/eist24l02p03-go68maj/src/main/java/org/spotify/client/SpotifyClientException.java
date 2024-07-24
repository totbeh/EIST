package org.spotify.client;

public class SpotifyClientException extends RuntimeException {
    @SuppressWarnings("unused")
    public SpotifyClientException() {}

    @SuppressWarnings("unused")
    public SpotifyClientException(String message) {
        super(message);
    }
}

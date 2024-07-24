package org.spotify.client;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import org.spotify.grpc.*;
import org.spotify.server.MetadataServiceImpl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpotifyClient {
    @SuppressWarnings("unused")
    private final SpotifyConnection spotifyConnection;

    @SuppressWarnings("unused")
    public SpotifyClient(Channel channel) {
        this.spotifyConnection = new SpotifyConnection(channel);
    }

    public SpotifyClient() throws SpotifyClientException {
        this.spotifyConnection = new SpotifyConnection();
    }


    public String displayPlaylist(int[] playlist) throws SpotifyClientException {
        if (playlist.length == 0) {
            return "";
        }

        EntitiesRequest.Builder requestBuilder = EntitiesRequest.newBuilder();
        for (int trackId : playlist) {
            requestBuilder.addId(trackId);
        }
        EntitiesRequest entitiesRequest = requestBuilder.build();
        Tracks tracks = spotifyConnection.getStub().getTrackMetadata(entitiesRequest);
        if (tracks.getTrackCount() == 0) {
            throw new SpotifyClientException("Failed to retrieve track metadata");
        }

        StringBuilder resultBuilder = new StringBuilder();
        for (int index = 0; index < tracks.getTrackCount(); index++) {
            Track track = tracks.getTrack(index);
            String trackInfo = String.format("%d. %s - %s (%s)\n",
                    index + 1,
                    track.getName(),
                    track.getArtist(),
                    formatSeconds(track.getDuration()));
            resultBuilder.append(trackInfo);
        }

        return resultBuilder.toString();
    }

    // Format seconds into minutes:seconds format
    public static String formatSeconds(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }


    public String displayAlbum(int id) throws SpotifyClientException {
        EntitiesRequest.Builder builder = EntitiesRequest.newBuilder();
        builder.addId(id);
        Albums albums = spotifyConnection.getStub().getAlbumMetadata(builder.build());

        if (albums.getAlbumCount() == 0) {
            throw new SpotifyClientException("Failed to get album metadata");
        }

        Album album = albums.getAlbum(0);

        EntitiesRequest.Builder trackRequest = EntitiesRequest.newBuilder();

        album.getTracksList().forEach(trackRequest::addId);

        Tracks tracks = spotifyConnection.getStub().getTrackMetadata(trackRequest.build());

        int totalDuration = tracks.getTrackList().stream()
                .mapToInt(Track::getDuration)
                .sum();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(album.getName())
                .append(" (")
                .append(formatSeconds(totalDuration))
                .append(")")
                .append('\n');

        String result = displayPlaylist(album.getTracksList().stream()
                .mapToInt(Integer::intValue)
                .toArray());

        String[] lines = result.split("\n");

        for (String line : lines) {
            stringBuilder.append("\t").append(line).append("\n");
        }

        return stringBuilder.toString();
    }


    public static void main(String[] args) throws SpotifyClientException {
        SpotifyClient client = new SpotifyClient();


        // Examples from Artemis
     //   System.out.println(client.displayPlaylist(new int[] {82763, 2791, 80673, 62523, 61703}));
        System.out.println(client.displayAlbum(24534));
    }
}

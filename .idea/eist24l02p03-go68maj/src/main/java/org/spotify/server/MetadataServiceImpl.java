package org.spotify.server;

import io.grpc.stub.StreamObserver;
import org.spotify.client.SpotifyClientException;
import org.spotify.db.Database;
import org.spotify.grpc.*;

public class MetadataServiceImpl extends MetadataServiceGrpc.MetadataServiceImplBase {
    private final Database database;

    @SuppressWarnings("unused")
    public MetadataServiceImpl(Database database) {
        this.database = database;
    }

    public MetadataServiceImpl() {
        this.database = new Database();
    }

    @Override
    public void getTrackMetadata(EntitiesRequest request, StreamObserver<Tracks> responseObserver) {
        Tracks.Builder builder = Tracks.newBuilder();
        for (int i = 0; i < request.getIdCount(); i++) {
            Track track = database.findTrackById(request.getId(i));
            if (track == null) {
                responseObserver.onNext(Tracks.newBuilder().build());
                responseObserver.onCompleted();
                return;

            }
            builder.addTrack(track);
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

    }


    public void getAlbumMetadata(EntitiesRequest request, StreamObserver<Albums> responseObserver) {
        Albums.Builder albumsBuilder = Albums.newBuilder();
        Album album = database.findAlbumById(request.getId(0));
        if (album == null) {
            responseObserver.onNext(Albums.newBuilder().build());
            responseObserver.onCompleted();
            return;
        }
        albumsBuilder.addAlbum(album);
        Albums albums = albumsBuilder.build();
        responseObserver.onNext(albums);
        responseObserver.onCompleted();
    }

}


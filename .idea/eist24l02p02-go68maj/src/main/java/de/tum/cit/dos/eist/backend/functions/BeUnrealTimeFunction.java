package de.tum.cit.dos.eist.backend.functions;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import de.tum.cit.dos.eist.backend.infrastructure.BeUnrealRepository;
import de.tum.cit.dos.eist.backend.infrastructure.FakeAwsSns;
import de.tum.cit.dos.eist.backend.infrastructure.FileStorage;
import de.tum.cit.dos.eist.backend.models.Post;
import de.tum.cit.dos.eist.backend.models.User;

public class BeUnrealTimeFunction
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private BeUnrealRepository repository;
    private FileStorage fileStorage;
    private FakeAwsSns awsSns;

    public BeUnrealTimeFunction() {
        repository = new BeUnrealRepository();
        fileStorage = new FileStorage();
        awsSns = new FakeAwsSns();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        List<User> userList = repository.getAllUsers();
        for (User user:userList) {
            awsSns.sendPushNotification(user, "Time to BeUnreal.");
        }
        deleteImage(null,null);
        return new APIGatewayProxyResponseEvent().withStatusCode(200);
    }

    private void deleteImage(String userId, String folderName) {
        List<User> userList = repository.getAllUsers();
        for (User friend: userList) {
            String key = getImagePath(friend.uid(), friend.hasPostedToday());
            fileStorage.deleteFile(key);
            repository.updateHasPostedToday(friend.uid(),false);
            String key2 = getImagePath(friend.uid(),true);
            fileStorage.deleteFile(key2);
        }
    }
    private String getImagePath(String userId, boolean hasUserPosted) {
        // Decide which image folder to use from FileStorage
        String folderName = "blurred_images";
        if (hasUserPosted) {
            folderName = "unblurred_images";
        }
        return folderName + "/" + userId + ".jpg";
    }
}
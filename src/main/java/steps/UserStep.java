package steps;

import data.request.UserRequest;
import data.response.UserResponse;

public interface UserStep {
    UserResponse createUser(UserRequest userRequest, int statusCode);

    void deleteUser(String accessToken, int statusCode);

    UserResponse loginUser(UserRequest userRequest, int statusCode);

    UserResponse changeUserData(UserRequest userRequest, int statusCode, String accessToken);

    UserResponse changeUserData(UserRequest userRequest, int statusCode);
}

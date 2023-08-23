package data.response;

import lombok.Getter;

@Getter
public class UserResponse {
    private boolean success;
    private UserDataResponse user;
    private String accessToken;
    private String refreshToken;
    private String message;
}

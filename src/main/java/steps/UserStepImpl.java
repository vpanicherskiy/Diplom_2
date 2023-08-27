package steps;

import data.request.UserRequest;
import data.response.UserResponse;
import data.specification.BaseRequestSpecification;
import data.specification.BaseResponseSpecification;
import data.specification.JsonSpecification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static data.specification.BaseRequestSpecification.*;

public class UserStepImpl implements UserStep {
    @Step("Cоздание пользователя")
    public UserResponse createUser(UserRequest userRequest, int statusCode) {
        String json = JsonSpecification.setGsonBuilder().toJson(userRequest);

        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .body(json)
                .post(REGISTRATION_USER)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .as(UserResponse.class);
    }

    public void deleteUser(String accessToken, int statusCode) {
        RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL,
                        ContentType.JSON))
                .header("Authorization", accessToken)
                .delete(DELETE_USER)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""));
    }

    public UserResponse loginUser(UserRequest userRequest, int statusCode) {
        String json = JsonSpecification.setGsonBuilder().toJson(userRequest);

        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .body(json)
                .post(LOGIN_USER)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .as(UserResponse.class);
    }

    public UserResponse changeUserData(UserRequest userRequest, int statusCode, String accessToken) {
        String json = JsonSpecification.setGsonBuilder().toJson(userRequest);

        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .body(json)
                .header("Authorization", accessToken)
                .patch(CHANGE_USER)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .as(UserResponse.class);
    }

    public UserResponse changeUserData(UserRequest userRequest, int statusCode) {
        String json = JsonSpecification.setGsonBuilder().toJson(userRequest);

        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .body(json)
                .patch(CHANGE_USER)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .as(UserResponse.class);
    }
}

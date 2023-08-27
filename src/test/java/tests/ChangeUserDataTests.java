package tests;

import data.providers.DataGenerator;
import data.request.UserRequest;
import data.response.UserResponse;
import data.specification.BaseResponseSpecification;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserStep;
import steps.UserStepImpl;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTests {
    private UserRequest userRequest;
    private UserStep userStep = new UserStepImpl();
    private UserResponse createdUser;
    private String accessToken;
    private String name;
    private String email;

    @Before
    public void before() {
        userRequest = new UserRequest(DataGenerator.getEmail(),
                DataGenerator.getPassword(),
                DataGenerator.getFullName());
        createdUser = userStep.createUser(userRequest, BaseResponseSpecification.SC_OK);
    }

    @Test()
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void changeUserDataWithAuth() {
        name = createdUser.getUser().getName();
        email = createdUser.getUser().getEmail();
        accessToken = createdUser.getAccessToken();

        userRequest = new UserRequest(DataGenerator.getEmail(),
                DataGenerator.getPassword(),
                DataGenerator.getFullName());
        createdUser = userStep.changeUserData(userRequest, BaseResponseSpecification.SC_OK, createdUser.getAccessToken());

        MatcherAssert.assertThat("Не удалось сменить email",
                createdUser.getUser().getEmail(),
                Matchers.not(equalTo(email)));
        MatcherAssert.assertThat("Не удалось сменить Имя",
                createdUser.getUser().getEmail(),
                Matchers.not(equalTo(name)));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void changeUserDataWithoutAuth() {
        name = createdUser.getUser().getName();
        email = createdUser.getUser().getEmail();
        accessToken = createdUser.getAccessToken();

        userRequest = new UserRequest(DataGenerator.getEmail(),
                DataGenerator.getPassword(),
                DataGenerator.getFullName());
        createdUser = userStep.changeUserData(userRequest, BaseResponseSpecification.SC_UNAUTHORIZED);

        MatcherAssert.assertThat("Удалось изменить данные без авторизации",
                createdUser.getMessage(),
                Matchers.equalTo("You should be authorised"));
    }

    @After
    public void after() {
        userStep.deleteUser(accessToken, BaseResponseSpecification.SC_ACCEPTED);
    }
}

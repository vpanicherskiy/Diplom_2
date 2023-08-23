package tests;


import data.providers.DataGenerator;
import data.request.UserRequest;
import data.response.UserResponse;
import data.specification.BaseResponseSpecification;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import steps.UserStep;
import steps.UserStepImpl;

public class CreateUserTests {
    private UserRequest userRequest;
    UserStep userStep = new UserStepImpl();
    UserResponse createdUser;

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUser() {
        userRequest = new UserRequest(DataGenerator.getEmail(),
                DataGenerator.getPassword(),
                DataGenerator.getFullName());
        createdUser = userStep.createUser(userRequest, BaseResponseSpecification.SC_OK);
        MatcherAssert.assertThat("Пользователь не создан",
                createdUser.isSuccess(),
                Matchers.equalTo(true));
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createRegisteredUser() {
        userRequest = new UserRequest("vladimirPan@mail.ru",
                "12345678",
                "Владимир");
        createdUser = userStep.createUser(userRequest, BaseResponseSpecification.SC_FORBIDDEN);
        MatcherAssert.assertThat("Удалось создать существующего пользователя",
                createdUser.isSuccess(),
                Matchers.equalTo(false));
        MatcherAssert.assertThat(createdUser.getMessage(),
                Matchers.equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя и не заполнить одно из обязательных полей")
    public void createdUserWithoutRequiredField() {
        userRequest = new UserRequest(DataGenerator.getEmail(), DataGenerator.getPassword());
        createdUser = userStep.createUser(userRequest, BaseResponseSpecification.SC_FORBIDDEN);
        MatcherAssert.assertThat("Удалось создать пользователя без почты",
                createdUser.isSuccess(),
                Matchers.equalTo(false));
        MatcherAssert.assertThat(createdUser.getMessage(),
                Matchers.equalTo("Email, password and name are required fields"));
    }

    @After
    public void after() {
        if (createdUser.isSuccess())
            userStep.deleteUser(createdUser.getAccessToken(), BaseResponseSpecification.SC_ACCEPTED);
    }
}

package tests;

import data.providers.DataGenerator;
import data.request.UserRequest;
import data.response.UserResponse;
import data.specification.BaseResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import steps.UserStep;
import steps.UserStepImpl;

public class LoginUserTests {
    private UserRequest userRequest;
    private UserStep userStep = new UserStepImpl();
    private UserResponse createdUser;

    @Test
    public void successLogin() {
        userRequest = new UserRequest("vladimirPan@mail.ru", "12345678");
        createdUser = userStep.loginUser(userRequest, BaseResponseSpecification.SC_OK);
        MatcherAssert.assertThat("Не удалось авторизоваться",
                createdUser.isSuccess(),
                Matchers.equalTo(true));
    }

    @Test
    public void invalidLogin() {
        userRequest = new UserRequest(DataGenerator.getEmail(), DataGenerator.getPassword());
        createdUser = userStep.loginUser(userRequest, BaseResponseSpecification.SC_UNAUTHORIZED);
        MatcherAssert.assertThat("Удалось авторизоваться с невалидными данными",
                createdUser.isSuccess(),
                Matchers.equalTo(false));
        MatcherAssert.assertThat(createdUser.getMessage(),
                Matchers.equalTo("email or password are incorrect"));
    }
}

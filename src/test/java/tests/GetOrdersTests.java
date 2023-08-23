package tests;

import data.providers.DataGenerator;
import data.request.CreateOrderRequest;
import data.request.UserRequest;
import data.response.CreateOrderResponse;
import data.response.GetIngredientResponse;
import data.response.GetOrdersResponse;
import data.response.UserResponse;
import data.specification.BaseResponseSpecification;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.OrderStepsImpl;
import steps.UserStep;
import steps.UserStepImpl;

import java.util.ArrayList;

public class GetOrdersTests {
    UserResponse createdUser;
    UserStep userStep = new UserStepImpl();
    OrderSteps orderSteps = new OrderStepsImpl();
    String accessToken;
    GetIngredientResponse ingredientResponse;
    CreateOrderResponse createOrderResponse;
    GetOrdersResponse getOrdersResponse;

    @Before
    public void before() {
        UserRequest userRequest = new UserRequest(DataGenerator.getEmail(),
                DataGenerator.getPassword(),
                DataGenerator.getFullName());
        createdUser = userStep.createUser(userRequest, BaseResponseSpecification.SC_OK);
        accessToken = createdUser.getAccessToken();

        int ingredientsCount = 2;
        ingredientResponse = orderSteps.getIngredients(BaseResponseSpecification.SC_OK);
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsCount; i++) {
            ingredients.add(ingredientResponse.getData().get(i).get_id());
        }

        CreateOrderRequest createOrderRequest = new CreateOrderRequest(ingredients);
        createOrderResponse = orderSteps.createOrderWithAuth(createOrderRequest,
                BaseResponseSpecification.SC_OK, accessToken);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя авторизованный пользователь")
    public void getOrdersWithAuth() {
        getOrdersResponse = orderSteps.getOrdersWithAuth(accessToken, BaseResponseSpecification.SC_OK);
        MatcherAssert.assertThat("Не удалось получить заказы",
                getOrdersResponse.isSuccess(),
                Matchers.equalTo(true));
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя неавторизованный пользователь")
    public void getOrdersWithoutAuth() {
        getOrdersResponse = orderSteps.getOrdersWithoutAuth(BaseResponseSpecification.SC_UNAUTHORIZED);
        MatcherAssert.assertThat("Удалось получить заказы для неавторизованного пользователя",
                getOrdersResponse.isSuccess(),
                Matchers.equalTo(false));
    }

    @After
    public void after() {
        userStep.deleteUser(accessToken, BaseResponseSpecification.SC_ACCEPTED);
    }
}

package tests;

import data.providers.DataGenerator;
import data.request.CreateOrderRequest;
import data.request.UserRequest;
import data.response.CreateOrderResponse;
import data.response.GetIngredientResponse;
import data.response.UserResponse;
import data.specification.BaseResponseSpecification;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.OrderStepsImpl;
import steps.UserStep;
import steps.UserStepImpl;

import java.util.ArrayList;

public class CreateOrderTests {
    private UserRequest userRequest;
    private UserStep userStep = new UserStepImpl();
    private UserResponse createdUser;
    private OrderSteps orderSteps = new OrderStepsImpl();
    private CreateOrderRequest createOrderRequest;
    private CreateOrderResponse createOrderResponse;
    private GetIngredientResponse ingredientResponse;
    private ArrayList<String> ingredients;
    private int ingredientsCount;
    private String accessToken;

    @Before
    public void before() {
        userRequest = new UserRequest(DataGenerator.getEmail(),
                DataGenerator.getPassword(),
                DataGenerator.getFullName());
        createdUser = userStep.createUser(userRequest, BaseResponseSpecification.SC_OK);
        accessToken = createdUser.getAccessToken();
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void createOrderWithAuth() {
        ingredientsCount = 2;
        ingredientResponse = orderSteps.getIngredients(BaseResponseSpecification.SC_OK);
        ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsCount; i++) {
            ingredients.add(ingredientResponse.getData().get(i).get_id());
        }

        createOrderRequest = new CreateOrderRequest(ingredients);
        createOrderResponse = orderSteps.createOrderWithAuth(createOrderRequest,
                BaseResponseSpecification.SC_OK, accessToken);
        MatcherAssert.assertThat("Заказ не создан",
                createOrderResponse.isSuccess(),
                Matchers.equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации с ингредиентами")
    public void createOrderWithoutAuth() {
        ingredientsCount = 2;
        ingredientResponse = orderSteps.getIngredients(BaseResponseSpecification.SC_OK);
        ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsCount; i++) {
            ingredients.add(ingredientResponse.getData().get(i).get_id());
        }

        createOrderRequest = new CreateOrderRequest(ingredients);
        createOrderResponse = orderSteps.createOrderWithoutAuth(createOrderRequest,
                BaseResponseSpecification.SC_OK);
        MatcherAssert.assertThat("Заказ не создан",
                createOrderResponse.isSuccess(),
                Matchers.equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    public void createOrderWithoutIngredients() {
        ingredients = new ArrayList<>();

        createOrderRequest = new CreateOrderRequest(ingredients);
        createOrderResponse = orderSteps.createOrderWithoutAuth(createOrderRequest,
                BaseResponseSpecification.SC_BAD_REQUEST);
        MatcherAssert.assertThat("Заказ создан без ингредиентов",
                createOrderResponse.isSuccess(),
                Matchers.equalTo(false));
    }

    @Test
    @DisplayName("Создание заказа без авторизации с неверным хешем ингредиентов")
    public void createOrderWithInvalidIngredients() {
        ingredientsCount = 2;
        ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsCount; i++) {
            ingredients.add(DataGenerator.getAnyString());
        }

        createOrderRequest = new CreateOrderRequest(ingredients);

        String response = orderSteps.createOrderInvalid(createOrderRequest,
                BaseResponseSpecification.SC_INTERNAL_SERVER_ERROR);

        Document document = Jsoup.parse(response, "order");
        Elements pre = document.select("pre");

        MatcherAssert.assertThat("Запрос успешно обработался",
                pre.text(),
                Matchers.equalTo("Internal Server Error"));
    }

    @After
    public void after() {
        userStep.deleteUser(accessToken, BaseResponseSpecification.SC_ACCEPTED);
    }
}

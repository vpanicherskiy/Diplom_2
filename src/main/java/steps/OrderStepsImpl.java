package steps;

import data.request.CreateOrderRequest;
import data.response.CreateOrderResponse;
import data.response.GetIngredientResponse;
import data.response.GetOrdersResponse;
import data.specification.BaseRequestSpecification;
import data.specification.BaseResponseSpecification;
import data.specification.JsonSpecification;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static data.specification.BaseRequestSpecification.*;

public class OrderStepsImpl implements OrderSteps {

    public CreateOrderResponse createOrderWithoutAuth(CreateOrderRequest createOrderRequest, int statusCode) {
        String json = JsonSpecification.setGsonBuilder().toJson(createOrderRequest);

        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .body(json)
                .post(CREATE_ORDER)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .as(CreateOrderResponse.class);
    }

    public CreateOrderResponse createOrderWithAuth(CreateOrderRequest createOrderRequest, int statusCode, String accessToken) {
        String json = JsonSpecification.setGsonBuilder().toJson(createOrderRequest);

        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .body(json)
                .header("Authorization", accessToken)
                .post(CREATE_ORDER)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .as(CreateOrderResponse.class);
    }

    public String createOrderInvalid(CreateOrderRequest createOrderRequest, int statusCode) {
        String json = JsonSpecification.setGsonBuilder().toJson(createOrderRequest);

        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .body(json)
                .post(CREATE_ORDER)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .asString();
    }

    public GetIngredientResponse getIngredients(int statusCode) {
        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .get(INGREDIENTS)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .as(GetIngredientResponse.class);
    }

    public GetOrdersResponse getOrdersWithAuth(String accessToken, int statusCode) {
        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .header("Authorization", accessToken)
                .get(GET_ORDERS)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .as(GetOrdersResponse.class);
    }

    public GetOrdersResponse getOrdersWithoutAuth(int statusCode) {
        return RestAssured.given()
                .spec(BaseRequestSpecification.baseRequestSpecification(BaseRequestSpecification.BASE_URL, ContentType.JSON))
                .get(GET_ORDERS)
                .then()
                .spec(BaseResponseSpecification.baseResponseSpecification(statusCode, ""))
                .extract()
                .body()
                .as(GetOrdersResponse.class);
    }
}

package steps;

import data.request.CreateOrderRequest;
import data.response.CreateOrderResponse;
import data.response.GetIngredientResponse;
import data.response.GetOrdersResponse;

public interface OrderSteps {
    CreateOrderResponse createOrderWithoutAuth(CreateOrderRequest createOrderRequest, int statusCode);

    CreateOrderResponse createOrderWithAuth(CreateOrderRequest createOrderRequest, int statusCode, String accessToken);

    String createOrderInvalid(CreateOrderRequest createOrderRequest, int statusCode);

    GetIngredientResponse getIngredients(int statusCode);

    GetOrdersResponse getOrdersWithAuth(String accessToken, int statusCode);

    GetOrdersResponse getOrdersWithoutAuth(int statusCode);
}

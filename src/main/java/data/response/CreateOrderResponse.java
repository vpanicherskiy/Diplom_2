package data.response;

import lombok.Getter;

@Getter
public class CreateOrderResponse {
    private String name;
    private CreateOrderOrderNumberResponse order;
    private boolean success;
    private String message;
}

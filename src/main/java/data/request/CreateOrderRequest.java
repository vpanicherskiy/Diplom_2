package data.request;


import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class CreateOrderRequest {
    private ArrayList<String> ingredients;
}

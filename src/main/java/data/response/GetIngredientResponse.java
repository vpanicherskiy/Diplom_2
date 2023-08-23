package data.response;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class GetIngredientResponse {
    private boolean success;
    private ArrayList<GetIngredientDataResponse> data;
}

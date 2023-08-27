package data.response;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class GetOrdersDataResponse {
    private ArrayList<String> ingredients;
    private String _id;
    private String status;
    private int number;
    private String createdAt;
    private String updatedAt;
}

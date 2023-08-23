package data.response;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class GetOrdersResponse {
    private boolean success;
    private ArrayList<GetOrdersDataResponse> orders;
    private int total;
    private int totalToday;
}

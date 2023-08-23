package data.specification;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSpecification {
    public static Gson setGsonBuilder() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
}

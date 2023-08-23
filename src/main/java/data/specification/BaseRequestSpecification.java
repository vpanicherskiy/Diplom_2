package data.specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseRequestSpecification {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String REGISTRATION_USER = "/api/auth/register";
    public static final String DELETE_USER = "/api/auth/user";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String CHANGE_USER = "/api/auth/user";
    public static final String CREATE_ORDER = "/api/orders";
    public static final String INGREDIENTS = "/api/ingredients";
    public static final String GET_ORDERS = "/api/orders";

    public static RequestSpecification baseRequestSpecification(String url, ContentType contentType) {
        LogDetail all = LogDetail.ALL;
        LogDetail uri = LogDetail.URI;
        LogDetail method = LogDetail.METHOD;

        return new RequestSpecBuilder()
                .setConfig(RestAssuredConfig.config().logConfig(LogConfig
                        .logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails(all)))
                .setBaseUri(url)
                .setRelaxedHTTPSValidation()
                .setContentType(contentType)
                .log(uri)
                .log(method)
                .build();
    }
}

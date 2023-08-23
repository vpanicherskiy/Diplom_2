package data.specification;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.parsing.Parser;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class BaseResponseSpecification {
    public static final int SC_OK = HttpStatus.SC_OK;
    public static final int SC_INTERNAL_SERVER_ERROR = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    public static final int SC_BAD_REQUEST = HttpStatus.SC_BAD_REQUEST;
    public static final int SC_UNAUTHORIZED = HttpStatus.SC_UNAUTHORIZED;
    public static final int SC_ACCEPTED = HttpStatus.SC_ACCEPTED;
    public static final int SC_FORBIDDEN = HttpStatus.SC_FORBIDDEN;

    public static ResponseSpecification baseResponseSpecification(int statusCode, String contentType) {
        Parser defaultParser = Parser.JSON;
        LogDetail status = LogDetail.STATUS;

        return new ResponseSpecBuilder()
                .setDefaultParser(defaultParser)
                .expectStatusCode(statusCode)
                .expectContentType(contentType)
                .log(status)
                .build();
    }
}

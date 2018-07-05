import Helpers.Helpers;
import org.testng.annotations.Test;

import static Helpers.CommonKeys.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestMovieEndpoint extends Helpers {


    @Test
    public void testValidMovieInfo() {
        given().when().get(urlBuilder(ENDPOINTS.MOVIE.getValue() + "/9472")).then()
                .statusCode(STATUS.STATUS_OKAY.getStatusCode())
                .body(ORIGINAL_TITLE, equalTo("DodgeBall: A True Underdog Story")
                        , TAGLINE, equalTo("Grab Life By The Ball")
                        , RUNTIME, equalTo(92));
    }

    @Test
    public void testNoIdFails() {
        given().when().get(urlBuilder(ENDPOINTS.MOVIE.getValue() + "/")).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage()));

    }

    @Test
    public void testNoApiKeyFails() {
        given().when().get(BASE_URL + ENDPOINTS.MOVIE.getValue() + "/9472").then()
                .statusCode(STATUS.BAD_REQUEST.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.BAD_REQUEST.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.BAD_REQUEST.getErrorMessage()));
    }

    @Test
    public void testMultiByteCharactersInID() {
        given().when().get(urlBuilder(ENDPOINTS.MOVIE.getValue() + "/" + MULTI_BYTE)).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage()));
    }

    @Test
    public void testMultiByteCharctersInApiKey(){
        given().when().get(urlBuilder(BASE_URL + ENDPOINTS.MOVIE.getValue() + "/9472?api_key=" + MULTI_BYTE)).then()
                .statusCode(STATUS.BAD_REQUEST.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.BAD_REQUEST.getErrorCode())
                        , STATUS_MESSAGE, equalTo(STATUS.BAD_REQUEST.getErrorMessage()));
    }

    @Test
    public void testLargeNumberAsID(){
        given().when().get(urlBuilder(ENDPOINTS.MOVIE.getValue() + "/12345678912345678912345678912345679")).then()
                .statusCode(STATUS.STATUS_OKAY.getStatusCode())
                .body(STATUS_CODE, equalTo(6)
                        , STATUS_MESSAGE, equalTo("Invalid id: The pre-requisite id is invalid or not found."));

    }


}
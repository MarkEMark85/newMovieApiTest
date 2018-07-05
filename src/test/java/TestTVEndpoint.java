import Helpers.Helpers;
import org.testng.annotations.Test;

import static Helpers.CommonKeys.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestTVEndpoint extends Helpers {

    @Test
    public void testLargeNumberInId(){
        given().when().get(urlBuilder(ENDPOINTS.TV.getValue() + "/123456789123456789123456789")).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage())
                ,STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode()));
    }

    @Test
    public void testMultiByteInID(){
        String multiBytes ="";
        for(int i = 0; i<50 ;i++){ multiBytes += MULTI_BYTE;}
        given().when().get(urlBuilder(ENDPOINTS.TV.getValue() + "/" + multiBytes)).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode())
                .body(STATUS_MESSAGE, equalTo(STATUS.NOT_FOUND.getErrorMessage())
                        ,STATUS_CODE, equalTo(STATUS.NOT_FOUND.getErrorCode()));
    }

    @Test
    public void testValidShowInfo(){
        given().when().get(urlBuilder(ENDPOINTS.TV.getValue() + "/1877")).then()
                .statusCode(STATUS.STATUS_OKAY.getStatusCode())
                .body(FIRST_AIR_DATE, equalTo("2007-08-17")
                        ,NAME, equalTo("Phineas and Ferb")
                        , GENRES_NAME, hasItem("Family"));
    }

    @Test
    public void testValidSeasons(){
        given().when().get(urlBuilder(ENDPOINTS.TV.getValue() + "/season/1877/3")).then()
                .statusCode(STATUS.STATUS_OKAY.getStatusCode())
        .body(AIR_DATE, equalTo("2011-03-04")
        ,EPISODE_NUMBER, equalTo(1)
        ,EPISODE_NAME, equalTo("The Great Indoors"));
    }

    @Test
    public void testNoApiKey(){
        given().when().get(BASE_URL + ENDPOINTS.TV.getValue() + "/1877?api_key=").then()
                .statusCode(STATUS.BAD_REQUEST.getStatusCode())
                .body(STATUS_CODE, equalTo(STATUS.BAD_REQUEST.getErrorCode())
                , STATUS_MESSAGE, equalTo(STATUS.BAD_REQUEST.getErrorMessage()));
    }
}

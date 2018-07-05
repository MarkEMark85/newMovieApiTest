import Helpers.Helpers;
import org.testng.annotations.Test;

import java.util.HashMap;
import static Helpers.CommonKeys.*;


import static io.restassured.RestAssured.*;


public class TestSearchEndpoint extends Helpers {

    @Test
    public void testMultibyteCharacthersInQuery(){
       String multiBytes = "";
        for(int i = 0; i<50; i++) {
            multiBytes += MULTI_BYTE;
        }

        given().when().get(urlCleanUp(ENDPOINTS.SEARCH.getValue() + "/" + ENDPOINTS.MOVIE.getValue(),multiBytes)).then()
                .statusCode(STATUS.NOT_FOUND.getStatusCode());
    }

    public String urlCleanUp(String endpoint, String query){
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("query", query);
        return urlBuilderWithParameters(endpoint, parameters);
    }
}

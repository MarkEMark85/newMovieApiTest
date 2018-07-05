package Helpers;

import java.util.HashMap;

public class Helpers {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "3f166dad68abb3adf2102fe18c2e99d7";
    //https://api.themoviedb.org/3/movie/9472?api_key=3f166dad68abb3adf2102fe18c2e99d7&language=en-US

    public enum ENDPOINTS {
        MOVIE("movie"),
        TV("tv"),
        SEARCH("search");

        private String value;

        ENDPOINTS(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum STATUS {
        STATUS_OKAY(200),
        NOT_FOUND(404, "The resource you requested could not be found.", 34),
        BAD_REQUEST(401, "Invalid API key: You must be granted a valid key.", 7);

        private Integer statusCode;
        private String errorMessage;
        private Integer errorCode;

        STATUS(Integer status) {
            statusCode = status;
        }

        STATUS(Integer status, String errorMessage, int errorCode) {
            statusCode = status;
            this.errorMessage = errorMessage;
            this.errorCode = errorCode;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public Integer getErrorCode() {
            return errorCode;
        }
    }

    public String urlBuilder(String endPoint) {
        return BASE_URL + endPoint + "?api_key=" + API_KEY;
    }

    public String urlBuilderWithParameters(String endpoint, HashMap<String, String> parameters) {
        String url = BASE_URL + endpoint + "?api_key=" + API_KEY;
        for (String el : parameters.keySet()) {
            url += "&" + el + "=" + parameters.get(el);
        }
        return url;

    }
}

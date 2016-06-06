package pl.edu.pw.ee.spacegame.server.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Michał on 2016-06-06.
 */
public class JsonResponseEntity<T> extends ResponseEntity<T> {

    private static HttpHeaders getJsonHeadersContentType() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return responseHeaders;
    }

    public JsonResponseEntity(HttpStatus statusCode) {
        super(getJsonHeadersContentType(), statusCode);
    }

    public JsonResponseEntity(T body, HttpStatus statusCode) {
        super(body, getJsonHeadersContentType(), statusCode);
    }
}

package pl.edu.pw.ee.spacegame.server.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Michał on 2016-06-06.
 */
public class TextResponceEntity<T> extends ResponseEntity<T> {

    private static HttpHeaders getTextHeadersContentType() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/plain");
        return responseHeaders;
    }

    public TextResponceEntity(HttpStatus statusCode) {
        super(getTextHeadersContentType(), statusCode);
    }

    public TextResponceEntity(T body, HttpStatus statusCode) {
        super(body, getTextHeadersContentType(), statusCode);
    }
}

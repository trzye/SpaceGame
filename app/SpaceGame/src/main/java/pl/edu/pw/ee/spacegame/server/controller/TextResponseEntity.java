package pl.edu.pw.ee.spacegame.server.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.utils.DatabaseLogger;

import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;

/**
 * Created by Michał on 2016-06-06.
 */
public class TextResponseEntity<T> extends ResponseEntity<T> {

    private static HttpHeaders getTextHeadersContentType() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/plain");
        return responseHeaders;
    }

    public TextResponseEntity(HttpStatus statusCode) {
        super(getTextHeadersContentType(), statusCode);
    }

    public TextResponseEntity(T body, HttpStatus statusCode) {
        super(body, getTextHeadersContentType(), statusCode);
    }

    public static ResponseEntity<?> getNotAuthorizedResponseEntity(AuthenticationData authenticationData, DatabaseLogger databaseLogger) {
        databaseLogger.info(String.format(NOT_AUTHORIZED_LOG, authenticationData.getNickname()));
        return new TextResponseEntity<>(NOT_LOGGED, HttpStatus.UNAUTHORIZED); //401
    }

    public static ResponseEntity<?> getNotActivatedResponseEntity(AuthenticationData authenticationData, DatabaseLogger databaseLogger) {
        databaseLogger.info(String.format(NOT_ACTIVATED_LOG, authenticationData.getNickname()));
        return new TextResponseEntity<>(NOT_ACTIVATED, HttpStatus.FORBIDDEN); //403
    }
}

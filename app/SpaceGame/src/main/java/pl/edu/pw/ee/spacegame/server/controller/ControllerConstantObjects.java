package pl.edu.pw.ee.spacegame.server.controller;

/**
 * Created by Michał on 2016-06-04.
 */
public class ControllerConstantObjects {
    //Patches
    public static final String SAMPLE_PATH = "/sample";
    public static final String SIGN_UP_PATH = "/signUp";

    //500 errors
    public static final String UNEXPECTED_ERROR = "Nastąpił niespodziwany błąd: ";

    //400 errors
    public static final String REQUEST_ERROR = "Błąd wysłanych danych: ";
    public static final String USER_EXISTS = "Użytkownik o podanym nicku już isnieje.";

    //logs
    public static final String USER_ADDED_LOG = "Użytkownik %s został dodany. Oczekiwanie na aktywację konta.";

    //200 information
    public static final String USER_ADDED = "Użytkownik został dodany. Link aktywacyjny został wysłany na adres %s i wygaśnie w ciągu dziesięciu minut.";
}

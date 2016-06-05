package pl.edu.pw.ee.spacegame.server.controller;

/**
 * Created by Micha� on 2016-06-04.
 */
public class ControllerConstantObjects {
    //Patches
    public static final String SAMPLE_PATH = "/sample";
    public static final String SIGN_UP_PATH = "/signUp";

    //500 errors
    public static final String UNEXPECTED_ERROR = "Nast�pi� niespodziwany b��d: ";

    //400 errors
    public static final String REQUEST_ERROR = "B��d wys�anych danych: ";
    public static final String USER_EXISTS = "U�ytkownik o podanym nicku ju� isnieje.";

    //logs
    public static final String USER_ADDED_LOG = "U�ytkownik %s zosta� dodany. Oczekiwanie na aktywacj� konta.";

    //200 information
    public static final String USER_ADDED = "U�ytkownik zosta� dodany. Link aktywacyjny zosta� wys�any na adres %s i wyga�nie w ci�gu dziesi�ciu minut.";
}

package pl.edu.pw.ee.spacegame.server.controller;

/**
 * Created by Micha³ on 2016-06-04.
 */
public class ControllerConstantObjects {
    //Patches
    public static final String SAMPLE_PATH = "/sample";
    public static final String SIGN_UP_PATH = "/signUp";

    //500 errors
    public static final String UNEXPECTED_ERROR = "Nast¹pi³ niespodziwany b³¹d: ";

    //400 errors
    public static final String REQUEST_ERROR = "B³¹d wys³anych danych: ";
    public static final String USER_EXISTS = "U¿ytkownik o podanym nicku ju¿ isnieje.";

    //logs
    public static final String USER_ADDED_LOG = "U¿ytkownik %s zosta³ dodany. Oczekiwanie na aktywacjê konta.";

    //200 information
    public static final String USER_ADDED = "U¿ytkownik zosta³ dodany. Link aktywacyjny zosta³ wys³any na adres %s i wygaœnie w ci¹gu dziesiêciu minut.";
}

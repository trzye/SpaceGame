package pl.edu.pw.ee.spacegame.server.controller;

/**
 * Created by Michał on 2016-06-04.
 */
public class ControllerConstantObjects {

    //Patches
    public static final String SAMPLE_PATH = "/sample";
    public static final String SIGN_UP_PATH = "/signUp";
    public static final String MAP_PATH = "/map";
    public static final String ACTIVATION_PATH = "/activation";
    public static final String SIGN_IN_PATH = "/signIn";

    //400 errors
    public static final String USER_EXISTS = "Użytkownik o podanym nicku już isnieje.";
    public static final String EMAIL_EXISTS = "Użytkownik o podanym mailu już isnieje.";
    public static final String MAIL_ERROR = "Podczas wysyłania maila wystąpił niespodziewany wyjątek";
    public static final String USER_NOT_EXISTS = "Nie istnieje użytkownik o podanej nazwie";
    public static final String WRONG_PASSWORD = "Złe hasło";
    public static final String WRONG_NICK_LENGTH = "Długość nick'a powinna być w zakresie <3;16>";
    public static final String WRONG_PASSWORD_LENGTH = "Długość hasła powinna być w zakresie <6;32>";
    public static final String PLANET_FIELD_NOT_EMPTY = "Planeta o podanych współrzędnych jest już zajęta lub tymczasowo zablokowana";
    public static final String BAD_COORDINATES = "Podane współrzędne planety są niewłaściwe";
    public static final String ACTIVATION_LOG = "Aktywowano konto dla email: %s";
    public static final String USER_WITH_SUCH_EMAIL_NOT_EXISTS = "Nie istnieje użytkownik o takim adresie email";
    public static final String BAD_ACTIVATION_CODE = "Błędny kod aktywacyjny";
    public static final String USER_ARLEADY_ACTIVATED = "Użytkownik został już aktywowany";
    public static final String ACTIVATION_TIMEOUT = "Przekroczono czas aktywacji";

    //logs
    public static final String UNEXPECTED_ERROR_LOG = "Nastąpił niespodziwany błąd: ";
    public static final String REQUEST_ERROR_LOG = "Błąd wysłanych danych: ";
    public static final String SAMPLE_LOG = "Getting GET request in sample controller";
    public static final String USER_ADDED_LOG = "Użytkownik %s został dodany. Oczekiwanie na aktywację konta.";
    public static final String USER_LOGGED_LOG = "Zalogowano użytkownika: %s";
    public static final String GET_MAP_LOG = "Wysłano informacje o mapie galaktyki";

    //200 information
    public static final String USER_ADDED = "Użytkownik został dodany. Link aktywacyjny został wysłany na adres %s i wygaśnie w ciągu dziesięciu minut.";
    public static final String ACTIVATION_SUCCESS = "Pomyślnie aktywowano konto";

    //others
    public static final String REST_SERVER = "localhost";
    public static final Integer REST_PORT = 8080;

    //mail
    public static final String EMAIL = "space.game.noreply@gmail.com";
    public static final String EMAIL_PASSWORD = "spacegamenoreply";
    public static final String SMTP_SERVER = "smtp.gmail.com";
    public static final Integer SMTP_PORT = 587;
    public static final String EMAIL_TITLE = "Link aktywacyjny do konta w SpaceGame";
    //TODO: zaprojektować ładniejszą wiadomość
    public static final String EMAIL_CONTENT = "<a href=\"%s\">Kliknij aby aktywować swoje konto w SpaceGame</a>";
    public static final String ACTIVATION_LINK = "http://" + REST_SERVER + ":" + REST_PORT + "/activation?email=%s&activationCode=%s";
}

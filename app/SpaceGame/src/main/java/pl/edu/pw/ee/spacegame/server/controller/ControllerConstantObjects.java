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
    public static final String MY_RESOURCES_PATH = "/myResources";
    public static final String MY_PLANET_PATH = "/myPlanet";
    public static final String MY_BUILDINGS_PATH = "/myBuildings";
    public static final String ATTACK_HISTORY_PATH = "/attackHistory";
    public static final String ALLIANCE_HISTORY_PATH = "/allianceHistory";
    public static final String MY_FLEET_PATH = "/myFleet";
    public static final String UPGRADE_BUILDING_PATH = "/upgradeBuilding";
    public static final String BUILD_SHIPS_PATH = "/buildShips";
    public static final String OTHER_PLANET_PATH = "/otherPlanet";
    public static final String GET_BACK_FLEET_PATH = "/getBackFleet";
    public static final String INCOMING_ATTACKS_AND_ALLIANCES_PATH = "/incomingAttacksAndAlliances";
    public static final String OUTGOING_ATTACKS_AND_ALLIANCES_PATH = "/outgoingAttacksAndAlliances";
    public static final String ALLIANCES_ON_MY_PLANET_PATH = "/alliancesOnMyPlanet";
    public static final String ATTACK_PATH = "/attack";
    public static final String HELP_PATH = "/help";


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
    public static final String USER_WITH_SUCH_EMAIL_NOT_EXISTS = "Nie istnieje użytkownik o takim adresie email (prawdopodobnie przekroczyłeś czas na aktywację konta - 10 minut)";
    public static final String BAD_ACTIVATION_CODE = "Błędny kod aktywacyjny";
    public static final String USER_ALREADY_ACTIVATED = "Użytkownik został już aktywowany";
    public static final String ACTIVATION_TIMEOUT = "Przekroczono czas aktywacji";
    public static final String WRONG_BUILDING_TYPE_ID = "Niepoprawny typ budynku do rozbudowania";
    public static final String MAX_BUILDING_LEVEL_UPGRADE = "Maksymalny poziom jest już osiągnięty, nie można rozbudować budynku.";
    public static final String NOT_ENOUGH_GADOLINIUM_FOR_BUILDING = "Za mało gadolinium aby rozbudować ten budynek";
    public static final String NOT_ENOUGH_UNUNTRIUM_FOR_BUILDING = "Nie masz wystarczająco ununtrium do wybudowania statków";
    public static final String CANT_BUILD_FLEET = "Nie można zbudować statków, flota nie jest na planecie macierzystej";
    public static final String CANT_BUILD_ZERO_FLEET = "Nie można wybudować zera lub mniej statków!";
    public static final String CANT_GET_BACK_FLEET = "Flota nie przebywa aktualnie na innej planecie, więc nie można jej cofnąć z wsparcia";
    public static final String NO_PLANET_ON_FIELD = "Nie ma na tym polu planety";
    public static final String CANT_CHOOSE_YOUR_PLANET = "Nie można wybrać własnej planety";
    public static final String CANT_ATTACK = "Nie można zaatakować, flota nie jest na planecie macierzystej";
    public static final String CANT_HELP = "Nie można wysłać pomocy, flota nie jest na planecie macierzystej";
    public static final String ZERO_FLEET = "Nie masz żadnych statków";


    //401
    public static final String NOT_LOGGED = "Nie jesteś zalogowany";

    //403
    public static final String NOT_ACTIVATED = "Twoje konto nie zostało aktywowane";

    //logs
    public static final String UNEXPECTED_ERROR_LOG = "Nastąpił niespodziwany błąd: ";
    public static final String REQUEST_ERROR_LOG = "Błąd wysłanych danych: ";
    public static final String SAMPLE_LOG = "Getting GET request in sample controller";
    public static final String USER_ADDED_LOG = "Użytkownik %s został dodany. Oczekiwanie na aktywację konta.";
    public static final String USER_LOGGED_LOG = "Zalogowano użytkownika: %s";
    public static final String GET_MAP_LOG = "Wysłano informacje o mapie galaktyki";
    public static final String NOT_AUTHORIZED_LOG = "Błąd autoryzacji użytkownika %s";
    public static final String NOT_ACTIVATED_LOG = "Użytkownik %s nie został aktywowany";
    public static final String GET_ATTACK_HISTORY_LOG = "Wysłano informacje o historii ataków";
    public static final String GET_ALLIANCE_HISTORY_LOG = "Wysłano informacje o historii sprzymierzeń";
    public static final String GET_OTHER_PLANET_LOG = "Wyświetlono planetę innego gracza";
    public static final String MY_BUILDINGS_LOG = "Zwróciłem dane o budynkach dla użytkownika %s";
    public static final String UPGRADE_BUILDING_LOG = "Zwiększono poziom budynku [id: %d] o 1";
    public static final String BUILD_FLEET = "Użytkownik %s wybudował statki [id: %d, ilość: %d]";
    public static final String GET_MY_FLEET_LOG = "Pobrano dane o flocie dla użytkownika %s";
    public static final String GET_MY_PLANET_LOG = "Pograno dane o planecie dla gracza %s";
    public static final String GET_MY_RESOURCES = "Pobranie danych o surowcach dla gracza %s";
    public static final String GET_BACK_FLEET_LOG = "Flota użytkownika %s dostała rozkaz powrotu";
    public static final String INCOMING_ATTACKS_AND_ALLIANCES_LOG = "Zwrócono informacje o nadchodzących flotach dla użytkownika %s";
    public static final String OUTGOING_ATTACKS_AND_ALLIANCES_LOG = "Zwrócono informacje o wychodzących flotach dla użytkownika %s";
    public static final String ALLIANCES_ON_MY_PLANET_LOG = "Zwrócono informację o sojuszniczej flocie na planecie gracza %s";
    public static final String ATTACK_PLANET_LOG = "Użytkownik %s zaatakował użytkownika %s";
    public static final String HELP_PLANET_LOG = "Użytkownik %s wysłał wsparcie do użytkownika %s";


    //200 information
    public static final String USER_ADDED = "Użytkownik został dodany. Link aktywacyjny został wysłany na adres %s i wygaśnie w ciągu dziesięciu minut.";
    public static final String ACTIVATION_SUCCESS = "Pomyślnie aktywowano konto";
    public static final String FLEET_BUILT = "Wybudowano statki";
    public static final String UPGRADE_BUILDING = "Zwiększono poziom budynku o 1";

    //others
    public static final String REST_SERVER = "localhost";
    public static final Integer REST_PORT = 8080;
    public static final Integer ACTIVATION_TIME_LIMIT = 1000 * 60 * 10; //1000 millis * 60 * 10= 10 minutes

    //mail
    public static final String EMAIL = "space.game.noreply@gmail.com";
    public static final String EMAIL_PASSWORD = "spacegamenoreply";
    public static final String SMTP_SERVER = "smtp.gmail.com";
    public static final Integer SMTP_PORT = 587;
    public static final String EMAIL_TITLE = "Link aktywacyjny do konta w SpaceGame";
    public static final String ACTIVATION_LINK = "http://" + REST_SERVER + ":" + REST_PORT + "/activation?email=%s&activationCode=%s";

    //byte
    public static final Byte ZERO_BYTE = 0;
    public static final Byte ONE_BYTE = 1;

}

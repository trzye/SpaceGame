package pl.edu.pw.ee.spacegame.server.game;

/**
 * Created by Michał on 2016-06-16.
 */
public class GameBalanceSettings {

    //odświeżanie stanu gry
    public static final long REFRESH_GAME_TIME = 15000; //co 15 sekund

    //maksymalne poziomy budynków
    public static final Integer UNUNTRIUM_MINE_MAX_LEVEL = 20;
    public static final Integer GADOLIN_MINE_MAX_LEVEL = 20;
    public static final Integer HANGAR_MAX_LEVEL = 15;
    public static final Integer DEFENCE_SYSTEMS_MAX_LEVEL = 15;

    //atak i obrona
    //łączny atak atakujących statków to jednocześnie pojemność na skradzione surowce
    public static final Integer WARSHIP_ATTACK = 20;
    public static final Integer BOMBER_ATTACK = 2 * WARSHIP_ATTACK;
    public static final Integer IRONCLADS_ATTACK = 2 * BOMBER_ATTACK;
    public static final Integer DEFENCE_SYSTEMS_ATTACK = 150; //obrona na jeden poziom budynku

    //'dokładność' generowania surowców (dla poziomu jeden budynku) np dla kopalni gadolinu:
    // 1000 = 1g/s
    // 1000 * 60 = 1g/m
    // 1000 * 60 * 60 = 1g/h
    public static final long MILLISECONDS_FOR_ONE_RESOURCE_UNIT = 60 * 1000;

    //czas jaki jest potrzebny do przebycia jednego pola na mapie
    public static final long MILLISECONDS_PER_FIELD = 60 * 1000; //jedna minuta
}

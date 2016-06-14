package pl.edu.pw.ee.spacegame.server.controller.otherplanet;

import java.util.ArrayList;

/**
 * Created by KTamo_000 on 2016-06-14.
 */
public class PlanetViewData {
    private String nickname;
    private int gadolin;
    private int ununtrium;
    private ArrayList<BuildingData> buildings;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGadolin() {
        return gadolin;
    }

    public void setGadolin(int gadolin) {
        this.gadolin = gadolin;
    }

    public int getUnuntrium() {
        return ununtrium;
    }

    public void setUnuntrium(int ununtrium) {
        this.ununtrium = ununtrium;
    }

    public ArrayList<BuildingData> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<BuildingData> buildings) {
        this.buildings = buildings;
    }
}

package pl.edu.pw.ee.spacegame.server.controller.fleets;

import java.util.ArrayList;

/**
 * Created by Michał on 2016-06-09.
 */
public class MyFleetData {
    public Integer status;
    public ArrayList<ShipsData> ships;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArrayList<ShipsData> getShips() {
        return ships;
    }

    public void setShips(ArrayList<ShipsData> ships) {
        this.ships = ships;
    }
}

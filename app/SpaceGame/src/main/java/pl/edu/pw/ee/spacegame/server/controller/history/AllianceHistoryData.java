package pl.edu.pw.ee.spacegame.server.controller.history;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by KTamo_000 on 2016-06-12.
 */
public class AllianceHistoryData {
    private Integer warships;
    private Integer bombers;
    private Integer ironclads;
    private String helpedPlanetName;
    private Byte result;
    private Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
    private String allyNickname;

    public Integer getWarships() {
        return warships;
    }

    public void setWarships(Integer warships) {
        this.warships = warships;
    }

    public Integer getBombers() {
        return bombers;
    }

    public void setBombers(Integer bombers) {
        this.bombers = bombers;
    }

    public Integer getIronclads() {
        return ironclads;
    }

    public void setIronclads(Integer ironclads) {
        this.ironclads = ironclads;
    }

    public String getHelpedPlanetName() {
        return helpedPlanetName;
    }

    public void setHelpedPlanetName(String attackedPlanetName) {
        this.helpedPlanetName = attackedPlanetName;
    }

    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getAllyNickname() {
        return allyNickname;
    }

    public void setAllyNickname(String attackerNickname) {
        this.allyNickname = attackerNickname;
    }
}

package pl.edu.pw.ee.spacegame.server.controller.history;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by KTamo_000 on 2016-06-09.
 */
public class AttackHistoryData {
    private Integer warships;
    private Integer bombers;
    private Integer ironclads;
    private String attackedPlanetName;
    private Integer gadolin;
    private Integer ununtrium;
    private Byte result;
    private Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
    private String attackerNickname;

    public String getAttackerNickname() {
        return attackerNickname;
    }

    public void setAttackerNickname(String attackerNickname) {
        this.attackerNickname = attackerNickname;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setAttackedPlanetName(String attackedPlanetName) {
        this.attackedPlanetName = attackedPlanetName;
    }

    public void setWarships(Integer warships) {
        this.warships = warships;
    }

    public void setBombers(Integer bombers) {
        this.bombers = bombers;
    }

    public void setIronclads(Integer ironclads) {
        this.ironclads = ironclads;
    }

    public void setResult(Byte result) {
        this.result = result;
    }

    public String getAttackedPlanetName() {
        return attackedPlanetName;
    }

    public Integer getWarships() {
        return warships;
    }

    public Integer getBombers() {
        return bombers;
    }

    public Integer getIronclads() {
        return ironclads;
    }

    public Byte getResult() {
        return result;
    }

    public AttackHistoryData() {
    }

    public Integer getGadolin() {
        return gadolin;
    }

    public void setGadolin(Integer gadolin) {
        this.gadolin = gadolin;
    }

    public Integer getUnuntrium() {
        return ununtrium;
    }

    public void setUnuntrium(Integer ununtrium) {
        this.ununtrium = ununtrium;
    }
}

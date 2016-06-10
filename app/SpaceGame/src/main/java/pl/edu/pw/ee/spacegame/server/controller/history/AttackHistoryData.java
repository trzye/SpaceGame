package pl.edu.pw.ee.spacegame.server.controller.history;

/**
 * Created by KTamo_000 on 2016-06-09.
 */
public class AttackHistoryData {
    private Integer warships;
    private Integer bombers;
    private Integer ironclads;
    private Integer attackedPlanetX;
    private Integer attackedPlanetY;
    private Byte result;

    public void setWarships(Integer warships) {
        this.warships = warships;
    }

    public void setBombers(Integer bombers) {
        this.bombers = bombers;
    }

    public void setIronclads(Integer ironclads) {
        this.ironclads = ironclads;
    }

    public void setAttackedPlanetX(Integer attackedPlanetX) {
        this.attackedPlanetX = attackedPlanetX;
    }

    public void setAttackedPlanetY(Integer attackedPlanetY) {
        this.attackedPlanetY = attackedPlanetY;
    }

    public void setResult(Byte result) {
        this.result = result;
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

    public Integer getAttackedPlanetX() {
        return attackedPlanetX;
    }

    public Integer getAttackedPlanetY() {
        return attackedPlanetY;
    }

    public Byte getResult() {
        return result;
    }

    public AttackHistoryData() {
    }
}

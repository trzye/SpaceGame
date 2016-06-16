package pl.edu.pw.ee.spacegame.server.controller.attacksandalliances;

/**
 * Created by Michał on 2016-06-16.
 */
public class AlliancesOnPlanetData {
    private Integer warships;
    private Integer bombers;
    private Integer ironclads;

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
}

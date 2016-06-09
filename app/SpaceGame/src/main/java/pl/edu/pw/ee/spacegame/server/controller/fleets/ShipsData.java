package pl.edu.pw.ee.spacegame.server.controller.fleets;

/**
 * Created by Michał on 2016-06-09.
 */
public class ShipsData {
    public Integer typeId;
    public Integer buildInUnuntiumCost;
    public Integer number;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getBuildInUnuntiumCost() {
        return buildInUnuntiumCost;
    }

    public void setBuildInUnuntiumCost(Integer buildInUnuntiumCost) {
        this.buildInUnuntiumCost = buildInUnuntiumCost;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}

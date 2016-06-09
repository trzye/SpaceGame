package pl.edu.pw.ee.spacegame.server.controller.building;

/**
 * Created by Michał on 2016-06-09.
 */
public class MyBuildingData {
    Integer typeId;
    Integer level;
    Integer maxLevel;
    Integer nextLevelInGadolinsCost;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Integer getNextLevelInGadolinsCost() {
        return nextLevelInGadolinsCost;
    }

    public void setNextLevelInGadolinsCost(Integer nextLevelInGadolinsCost) {
        this.nextLevelInGadolinsCost = nextLevelInGadolinsCost;
    }
}

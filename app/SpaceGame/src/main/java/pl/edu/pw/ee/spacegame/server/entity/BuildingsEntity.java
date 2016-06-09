package pl.edu.pw.ee.spacegame.server.entity;

import pl.edu.pw.ee.spacegame.server.dao.crud.BuildingsDicDAO;

import javax.persistence.*;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "buildings", schema = "", catalog = "spacegame")
public class BuildingsEntity {

    public enum ID {
        UNUNTRIUM_MINE_ID,
        GADOLIN_MINE_ID,
        HANGAR_ID,
        DEFENCE_SYSTEMS_ID
    }

    public static final String GADOLIN_MINE = "Gadolin mine";
    public static final String UNUNTRIUM_MINE = "Ununtrium mine";
    public static final String HANGAR = "Hangar";
    public static final String DEFENCE_SYSTEMS = "Defence systems";

    public static final Integer UNUNTRIUM_MINE_MAX_LEVEL = 20;
    public static final Integer GADOLIN_MINE_MAX_LEVEL = 20;
    public static final Integer HANGAR_MAX_LEVEL = 15;
    public static final Integer DEFENCE_SYSTEMS_MAX_LEVEL = 15;

    public static final Integer UNUNTRIUM_COST = 120;
    public static final Integer GADOLIN_COST = 150;
    public static final Integer HANGAR_COST = 120;
    public static final Integer DEFENCE_COST = 100;

    private Integer buildingId;
    private Integer level;
    private BuildingsDicEntity buildingsDicByBuildingsDicId;
    private PlanetsEntity planetsByPlanetId;

    public static BuildingsEntity getUnuntriumMine(BuildingsDicDAO buildingsDicDAO) {
        BuildingsEntity building = new BuildingsEntity();
        building.setLevel(1);
        building.setBuildingsDicByBuildingsDicId(buildingsDicDAO.findOne(0));
        return building;
    }

    public static BuildingsEntity getGadolinMine(BuildingsDicDAO buildingsDicDAO) {
        BuildingsEntity building = new BuildingsEntity();
        building.setLevel(1);
        building.setBuildingsDicByBuildingsDicId(buildingsDicDAO.findOne(1));
        return building;
    }

    public static BuildingsEntity getHangar(BuildingsDicDAO buildingsDicDAO) {
        BuildingsEntity building = new BuildingsEntity();
        building.setLevel(1);
        building.setBuildingsDicByBuildingsDicId(buildingsDicDAO.findOne(2));
        return building;
    }

    public static BuildingsEntity getDefenceSystems(BuildingsDicDAO buildingsDicDAO) {
        BuildingsEntity building = new BuildingsEntity();
        building.setLevel(1);
        building.setBuildingsDicByBuildingsDicId(buildingsDicDAO.findOne(3));
        return building;
    }

    @Id
    @GeneratedValue
    @Column(name = "building_id")
    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingsEntity that = (BuildingsEntity) o;

        if (buildingId != null ? !buildingId.equals(that.buildingId) : that.buildingId != null) return false;
        return !(level != null ? !level.equals(that.level) : that.level != null);

    }

    @Override
    public int hashCode() {
        int result = buildingId != null ? buildingId.hashCode() : 0;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "buildings_dic_id", referencedColumnName = "buildings_dic_id", nullable = false)
    public BuildingsDicEntity getBuildingsDicByBuildingsDicId() {
        return buildingsDicByBuildingsDicId;
    }

    public void setBuildingsDicByBuildingsDicId(BuildingsDicEntity buildingsDicByBuildingsDicId) {
        this.buildingsDicByBuildingsDicId = buildingsDicByBuildingsDicId;
    }

    @ManyToOne
    @JoinColumn(name = "planet_id", referencedColumnName = "planet_id", nullable = false)
    public PlanetsEntity getPlanetsByPlanetId() {
        return planetsByPlanetId;
    }

    public void setPlanetsByPlanetId(PlanetsEntity planetsByPlanetId) {
        this.planetsByPlanetId = planetsByPlanetId;
    }
}

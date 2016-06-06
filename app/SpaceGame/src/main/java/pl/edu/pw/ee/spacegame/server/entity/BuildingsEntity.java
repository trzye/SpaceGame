package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "buildings", schema = "", catalog = "spacegame")
public class BuildingsEntity {
    private Integer buildingId;
    private Integer level;
    private BuildingsDicEntity buildingsDicByBuildingsDicId;
    private PlanetsEntity planetsByPlanetId;

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

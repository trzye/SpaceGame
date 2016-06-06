package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "buildings_dic", schema = "", catalog = "spacegame")
public class BuildingsDicEntity {
    private Integer buildingsDicId;
    private String name;
    private Collection<BuildingsEntity> buildingsesByBuildingsDicId;

    @Id
    @GeneratedValue
    @Column(name = "buildings_dic_id")
    public Integer getBuildingsDicId() {
        return buildingsDicId;
    }

    public void setBuildingsDicId(Integer buildingsDicId) {
        this.buildingsDicId = buildingsDicId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingsDicEntity that = (BuildingsDicEntity) o;

        if (buildingsDicId != null ? !buildingsDicId.equals(that.buildingsDicId) : that.buildingsDicId != null)
            return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = buildingsDicId != null ? buildingsDicId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "buildingsDicByBuildingsDicId")
    public Collection<BuildingsEntity> getBuildingsesByBuildingsDicId() {
        return buildingsesByBuildingsDicId;
    }

    public void setBuildingsesByBuildingsDicId(Collection<BuildingsEntity> buildingsesByBuildingsDicId) {
        this.buildingsesByBuildingsDicId = buildingsesByBuildingsDicId;
    }
}

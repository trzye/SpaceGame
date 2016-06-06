package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "planet_fields", schema = "", catalog = "spacegame")
public class PlanetFieldsEntity {
    private Integer planetFieldId;
    private Integer coordinateX;
    private Integer coordinateY;
    private Status status = Status.LOCKED;
    private Collection<ActivationsEntity> activationsesByPlanetFieldId;
    private Collection<PlanetsEntity> planetsesByPlanetFieldId;

    @Id
    @GeneratedValue
    @Column(name = "planet_field_id")
    public Integer getPlanetFieldId() {
        return planetFieldId;
    }

    public void setPlanetFieldId(Integer planetFieldId) {
        this.planetFieldId = planetFieldId;
    }

    @Basic
    @Column(name = "coordinate_x")
    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    @Basic
    @Column(name = "coordinate_y")
    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanetFieldsEntity that = (PlanetFieldsEntity) o;

        if (planetFieldId != null ? !planetFieldId.equals(that.planetFieldId) : that.planetFieldId != null)
            return false;
        if (coordinateX != null ? !coordinateX.equals(that.coordinateX) : that.coordinateX != null) return false;
        if (coordinateY != null ? !coordinateY.equals(that.coordinateY) : that.coordinateY != null) return false;
        return !(status != null ? !status.equals(that.status) : that.status != null);

    }

    @Override
    public int hashCode() {
        int result = planetFieldId != null ? planetFieldId.hashCode() : 0;
        result = 31 * result + (coordinateX != null ? coordinateX.hashCode() : 0);
        result = 31 * result + (coordinateY != null ? coordinateY.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "planetFieldsByPlanetFieldId")
    public Collection<ActivationsEntity> getActivationsesByPlanetFieldId() {
        return activationsesByPlanetFieldId;
    }

    public void setActivationsesByPlanetFieldId(Collection<ActivationsEntity> activationsesByPlanetFieldId) {
        this.activationsesByPlanetFieldId = activationsesByPlanetFieldId;
    }

    @OneToMany(mappedBy = "planetFieldsByPlanetFieldId")
    public Collection<PlanetsEntity> getPlanetsesByPlanetFieldId() {
        return planetsesByPlanetFieldId;
    }

    public void setPlanetsesByPlanetFieldId(Collection<PlanetsEntity> planetsesByPlanetFieldId) {
        this.planetsesByPlanetFieldId = planetsesByPlanetFieldId;
    }

    public enum Status {
        LOCKED,
        EMPTY,
        USED
    }
}

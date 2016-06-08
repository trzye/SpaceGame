package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "resources", schema = "", catalog = "spacegame")
public class ResourcesEntity {
    private Integer resourceId;
    private Integer gadolin;
    private Integer ununtrium;
    private Timestamp lastUpdate = new Timestamp(Calendar.getInstance().getTime().getTime());
    private Collection<PlanetsEntity> planetsesByResourceId;

    public static ResourcesEntity getStartingState() {
        ResourcesEntity resourcesEntity = new ResourcesEntity();
        resourcesEntity.setGadolin(0);
        resourcesEntity.setUnuntrium(0);
        return resourcesEntity;
    }

    @Id
    @GeneratedValue
    @Column(name = "resource_id")
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Basic
    @Column(name = "gadolin")
    public Integer getGadolin() {
        return gadolin;
    }

    public void setGadolin(Integer gadolin) {
        this.gadolin = gadolin;
    }

    @Basic
    @Column(name = "ununtrium")
    public Integer getUnuntrium() {
        return ununtrium;
    }

    public void setUnuntrium(Integer ununtrium) {
        this.ununtrium = ununtrium;
    }

    @Basic
    @Column(name = "last_update")
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourcesEntity that = (ResourcesEntity) o;

        if (resourceId != null ? !resourceId.equals(that.resourceId) : that.resourceId != null) return false;
        if (gadolin != null ? !gadolin.equals(that.gadolin) : that.gadolin != null) return false;
        if (ununtrium != null ? !ununtrium.equals(that.ununtrium) : that.ununtrium != null) return false;
        return !(lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null);

    }

    @Override
    public int hashCode() {
        int result = resourceId != null ? resourceId.hashCode() : 0;
        result = 31 * result + (gadolin != null ? gadolin.hashCode() : 0);
        result = 31 * result + (ununtrium != null ? ununtrium.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "resourcesByResourceId")
    public Collection<PlanetsEntity> getPlanetsesByResourceId() {
        return planetsesByResourceId;
    }

    public void setPlanetsesByResourceId(Collection<PlanetsEntity> planetsesByResourceId) {
        this.planetsesByResourceId = planetsesByResourceId;
    }
}

package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "current_alliances", schema = "", catalog = "spacegame")
public class CurrentAlliancesEntity {
    private Integer currentAllianceId;
    private Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
    private PlanetsEntity planetsByHelpedPlanetId;
    private FleetsEntity fleetsByFleetId;

    @Id
    @GeneratedValue
    @Column(name = "current_alliance_id")
    public Integer getCurrentAllianceId() {
        return currentAllianceId;
    }

    public void setCurrentAllianceId(Integer currentAllianceId) {
        this.currentAllianceId = currentAllianceId;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentAlliancesEntity that = (CurrentAlliancesEntity) o;

        if (currentAllianceId != null ? !currentAllianceId.equals(that.currentAllianceId) : that.currentAllianceId != null)
            return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = currentAllianceId != null ? currentAllianceId.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "helped_planet_id", referencedColumnName = "planet_id")
    public PlanetsEntity getPlanetsByHelpedPlanetId() {
        return planetsByHelpedPlanetId;
    }

    public void setPlanetsByHelpedPlanetId(PlanetsEntity planetsByHelpedPlanetId) {
        this.planetsByHelpedPlanetId = planetsByHelpedPlanetId;
    }

    @ManyToOne
    @JoinColumn(name = "fleet_id", referencedColumnName = "fleet_id", nullable = false)
    public FleetsEntity getFleetsByFleetId() {
        return fleetsByFleetId;
    }

    public void setFleetsByFleetId(FleetsEntity fleetsByFleetId) {
        this.fleetsByFleetId = fleetsByFleetId;
    }

    public static CurrentAlliancesEntity getStartingState(FleetsEntity fleetsEntity) {
        CurrentAlliancesEntity currentAlliancesEntity = new CurrentAlliancesEntity();
        currentAlliancesEntity.setFleetsByFleetId(fleetsEntity);
        return currentAlliancesEntity;
    }
}

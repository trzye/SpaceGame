package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "current_alliances", schema = "", catalog = "spacegame")
public class CurrentAlliancesEntity {
    private Integer currentAllianceId;
    private Timestamp timeOfSendingAlliance;
    private Timestamp timeOfComing;
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
    @Column(name = "time_of_sending_alliance")
    public Timestamp getTimeOfSendingAlliance() {
        return timeOfSendingAlliance;
    }

    public void setTimeOfSendingAlliance(Timestamp timeOfSendingAlliance) {
        this.timeOfSendingAlliance = timeOfSendingAlliance;
    }

    @Basic
    @Column(name = "time_of_coming")
    public Timestamp getTimeOfComing() {
        return timeOfComing;
    }

    public void setTimeOfComing(Timestamp timeOfComing) {
        this.timeOfComing = timeOfComing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentAlliancesEntity that = (CurrentAlliancesEntity) o;

        if (currentAllianceId != null ? !currentAllianceId.equals(that.currentAllianceId) : that.currentAllianceId != null)
            return false;
        if (timeOfSendingAlliance != null ? !timeOfSendingAlliance.equals(that.timeOfSendingAlliance) : that.timeOfSendingAlliance != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = currentAllianceId != null ? currentAllianceId.hashCode() : 0;
        result = 31 * result + (timeOfSendingAlliance != null ? timeOfSendingAlliance.hashCode() : 0);
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

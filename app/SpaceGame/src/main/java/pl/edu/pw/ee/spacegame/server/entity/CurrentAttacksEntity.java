package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "current_attacks", schema = "", catalog = "spacegame")
public class CurrentAttacksEntity {
    private Integer currentAttackId;
    private Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
    private PlanetsEntity planetsByAttackedPlanetId;
    private FleetsEntity fleetsByFleetId;

    @Id
    @GeneratedValue
    @Column(name = "current_attack_id")
    public Integer getCurrentAttackId() {
        return currentAttackId;
    }

    public void setCurrentAttackId(Integer currentAttackId) {
        this.currentAttackId = currentAttackId;
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

        CurrentAttacksEntity that = (CurrentAttacksEntity) o;

        if (currentAttackId != null ? !currentAttackId.equals(that.currentAttackId) : that.currentAttackId != null)
            return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = currentAttackId != null ? currentAttackId.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "attacked_planet_id", referencedColumnName = "planet_id")
    public PlanetsEntity getPlanetsByAttackedPlanetId() {
        return planetsByAttackedPlanetId;
    }

    public void setPlanetsByAttackedPlanetId(PlanetsEntity planetsByAttackedPlanetId) {
        this.planetsByAttackedPlanetId = planetsByAttackedPlanetId;
    }

    @ManyToOne
    @JoinColumn(name = "fleet_id", referencedColumnName = "fleet_id", nullable = false)
    public FleetsEntity getFleetsByFleetId() {
        return fleetsByFleetId;
    }

    public void setFleetsByFleetId(FleetsEntity fleetsByFleetId) {
        this.fleetsByFleetId = fleetsByFleetId;
    }

    public static CurrentAttacksEntity getStartingState(FleetsEntity fleetsEntity) {
        CurrentAttacksEntity currentAttacksEntity = new CurrentAttacksEntity();
        currentAttacksEntity.setFleetsByFleetId(fleetsEntity);
        return currentAttacksEntity;
    }
}

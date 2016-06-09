package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "fleets", schema = "", catalog = "spacegame")
public class FleetsEntity {

    public enum FleetStatus {
        ON_THE_MOTHER_PLANET,
        IN_THE_WAY_TO_ATTACK,
        COMMING_BACK_FROM_ATTACK,
        IN_THE_WAY_TO_HELP,
        COMMING_BACK_FROM_HELP,
        ON_THE_OTHER_PLANET
    }

    public enum FleetType {
        WARSHIP,
        BOMBER,
        IRONCLADS,
    }

    public static final Integer BASE_WARSHIP_COST = 20;
    public static final Integer BASE_BOMBER_COST = 40;
    public static final Integer BASE_IRONCLADS_COST = 80;

    private Integer fleetId;
    private Integer warships;
    private Integer bombers;
    private Integer ironclads;
    private Collection<CurrentAlliancesEntity> currentAlliancesByFleetId;
    private Collection<CurrentAttacksEntity> currentAttacksesByFleetId;
    private Collection<PlanetsEntity> planetsesByFleetId;

    public static FleetsEntity getStartingState() {
        FleetsEntity fleets = new FleetsEntity();
        fleets.setWarships(0);
        fleets.setIronclads(0);
        fleets.setBombers(0);
        return fleets;
    }

    @Id
    @GeneratedValue
    @Column(name = "fleet_id")
    public Integer getFleetId() {
        return fleetId;
    }

    public void setFleetId(Integer fleetId) {
        this.fleetId = fleetId;
    }

    @Basic
    @Column(name = "warships")
    public Integer getWarships() {
        return warships;
    }

    public void setWarships(Integer warships) {
        this.warships = warships;
    }

    @Basic
    @Column(name = "bombers")
    public Integer getBombers() {
        return bombers;
    }

    public void setBombers(Integer bombers) {
        this.bombers = bombers;
    }

    @Basic
    @Column(name = "ironclads")
    public Integer getIronclads() {
        return ironclads;
    }

    public void setIronclads(Integer ironclads) {
        this.ironclads = ironclads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FleetsEntity that = (FleetsEntity) o;

        if (fleetId != null ? !fleetId.equals(that.fleetId) : that.fleetId != null) return false;
        if (warships != null ? !warships.equals(that.warships) : that.warships != null) return false;
        if (bombers != null ? !bombers.equals(that.bombers) : that.bombers != null) return false;
        return !(ironclads != null ? !ironclads.equals(that.ironclads) : that.ironclads != null);

    }

    @Override
    public int hashCode() {
        int result = fleetId != null ? fleetId.hashCode() : 0;
        result = 31 * result + (warships != null ? warships.hashCode() : 0);
        result = 31 * result + (bombers != null ? bombers.hashCode() : 0);
        result = 31 * result + (ironclads != null ? ironclads.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "fleetsByFleetId")
    public Collection<CurrentAlliancesEntity> getCurrentAlliancesByFleetId() {
        return currentAlliancesByFleetId;
    }

    public void setCurrentAlliancesByFleetId(Collection<CurrentAlliancesEntity> currentAlliancesByFleetId) {
        this.currentAlliancesByFleetId = currentAlliancesByFleetId;
    }

    @OneToMany(mappedBy = "fleetsByFleetId")
    public Collection<CurrentAttacksEntity> getCurrentAttacksesByFleetId() {
        return currentAttacksesByFleetId;
    }

    public void setCurrentAttacksesByFleetId(Collection<CurrentAttacksEntity> currentAttacksesByFleetId) {
        this.currentAttacksesByFleetId = currentAttacksesByFleetId;
    }

    @OneToMany(mappedBy = "fleetsByFleetId")
    public Collection<PlanetsEntity> getPlanetsesByFleetId() {
        return planetsesByFleetId;
    }

    public void setPlanetsesByFleetId(Collection<PlanetsEntity> planetsesByFleetId) {
        this.planetsesByFleetId = planetsesByFleetId;
    }
}

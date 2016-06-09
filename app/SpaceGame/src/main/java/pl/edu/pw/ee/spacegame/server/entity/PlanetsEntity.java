package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "planets", schema = "", catalog = "spacegame")
public class PlanetsEntity {
    private Integer planetId;
    private String name;
    private Collection<BuildingsEntity> buildingsesByPlanetId;
    private Collection<CurrentAlliancesEntity> currentAlliancesByPlanetId;
    private Collection<CurrentAttacksEntity> currentAttacksesByPlanetId;
    private Collection<AllianceHistoriesEntity> allianceHistoriesByPlanetId;
    private Collection<AttackHistoriesEntity> attackHistoriesByPlanetId;
    private UsersEntity usersByUserId;
    private ResourcesEntity resourcesByResourceId;
    private FleetsEntity fleetsByFleetId;
    private PlanetFieldsEntity planetFieldsByPlanetFieldId;

    @Id
    @GeneratedValue
    @Column(name = "planet_id")
    public Integer getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Integer planetId) {
        this.planetId = planetId;
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

        PlanetsEntity that = (PlanetsEntity) o;

        if (planetId != null ? !planetId.equals(that.planetId) : that.planetId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = planetId != null ? planetId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "planetsByPlanetId")
    public Collection<BuildingsEntity> getBuildingsesByPlanetId() {
        return buildingsesByPlanetId;
    }

    public void setBuildingsesByPlanetId(Collection<BuildingsEntity> buildingsesByPlanetId) {
        this.buildingsesByPlanetId = buildingsesByPlanetId;
    }

    @OneToMany(mappedBy = "planetsByHelpedPlanetId")
    public Collection<CurrentAlliancesEntity> getCurrentAlliancesByPlanetId() {
        return currentAlliancesByPlanetId;
    }

    public void setCurrentAlliancesByPlanetId(Collection<CurrentAlliancesEntity> currentAlliancesByPlanetId) {
        this.currentAlliancesByPlanetId = currentAlliancesByPlanetId;
    }

    @OneToMany(mappedBy = "planetsByAttackedPlanetId")
    public Collection<CurrentAttacksEntity> getCurrentAttacksesByPlanetId() {
        return currentAttacksesByPlanetId;
    }

    public void setCurrentAttacksesByPlanetId(Collection<CurrentAttacksEntity> currentAttacksesByPlanetId) {
        this.currentAttacksesByPlanetId = currentAttacksesByPlanetId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "resource_id", referencedColumnName = "resource_id", nullable = false)
    public ResourcesEntity getResourcesByResourceId() {
        return resourcesByResourceId;
    }

    public void setResourcesByResourceId(ResourcesEntity resourcesByResourceId) {
        this.resourcesByResourceId = resourcesByResourceId;
    }

    @ManyToOne
    @JoinColumn(name = "fleet_id", referencedColumnName = "fleet_id", nullable = false)
    public FleetsEntity getFleetsByFleetId() {
        return fleetsByFleetId;
    }

    public void setFleetsByFleetId(FleetsEntity fleetsByFleetId) {
        this.fleetsByFleetId = fleetsByFleetId;
    }

    @ManyToOne
    @JoinColumn(name = "planet_field_id", referencedColumnName = "planet_field_id", nullable = false)
    public PlanetFieldsEntity getPlanetFieldsByPlanetFieldId() {
        return planetFieldsByPlanetFieldId;
    }

    public void setPlanetFieldsByPlanetFieldId(PlanetFieldsEntity planetFieldsByPlanetFieldId) {
        this.planetFieldsByPlanetFieldId = planetFieldsByPlanetFieldId;
    }

    @OneToMany(mappedBy = "planetsByHelpedPlanetId")
    public Collection<AllianceHistoriesEntity> getAllianceHistoriesByPlanetId() {
        return allianceHistoriesByPlanetId;
    }

    public void setAllianceHistoriesByPlanetId(Collection<AllianceHistoriesEntity> allianceHistoriesByPlanetId) {
        this.allianceHistoriesByPlanetId = allianceHistoriesByPlanetId;
    }

    @OneToMany(mappedBy = "planetsByAttackedPlanetId")
    public Collection<AttackHistoriesEntity> getAttackHistoriesByPlanetId() {
        return attackHistoriesByPlanetId;
    }

    public void setAttackHistoriesByPlanetId(Collection<AttackHistoriesEntity> attackHistoriesByPlanetId) {
        this.attackHistoriesByPlanetId = attackHistoriesByPlanetId;
    }

    @Transient
    public BuildingsEntity getUnuntriumMine() {
        return getBuildingsEntity(BuildingsEntity.UNUNTRIUM_MINE);
    }

    @Transient
    public BuildingsEntity getGadolinMine() {
        return getBuildingsEntity(BuildingsEntity.GADOLIN_MINE);
    }

    @Transient
    public BuildingsEntity getHangar() {
        return getBuildingsEntity(BuildingsEntity.HANGAR);
    }

    @Transient
    public BuildingsEntity getDefenceSystems() {
        return getBuildingsEntity(BuildingsEntity.DEFENCE_SYSTEMS);
    }

    /*
    Dzięki temu JPA nie będzie próbować "zamapować" tej metody
    */
    @Transient
    private BuildingsEntity getBuildingsEntity(String name) {
        for (BuildingsEntity buildingsEntity : getBuildingsesByPlanetId()) {
            if (buildingsEntity.getBuildingsDicByBuildingsDicId().getName().equals(name)) {
                return buildingsEntity;
            }
        }
        return null;
    }

    @Transient
    public CurrentAlliancesEntity getCurrentAlliances() {
        for (CurrentAlliancesEntity alliance : fleetsByFleetId.getCurrentAlliancesByFleetId()) {
            return alliance;
        }
        return null;
    }

    @Transient
    public CurrentAttacksEntity getCurrentAttacks() {
        for (CurrentAttacksEntity attack : fleetsByFleetId.getCurrentAttacksesByFleetId()) {
            return attack;
        }
        return null;
    }
}

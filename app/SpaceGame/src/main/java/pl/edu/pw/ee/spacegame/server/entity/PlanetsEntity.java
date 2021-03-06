package pl.edu.pw.ee.spacegame.server.entity;

import pl.edu.pw.ee.spacegame.server.controller.fleets.MyFleetData;
import pl.edu.pw.ee.spacegame.server.controller.fleets.ShipsData;
import pl.edu.pw.ee.spacegame.server.game.FleetCosts;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static pl.edu.pw.ee.spacegame.server.game.GameBalanceSettings.MILLISECONDS_PER_FIELD;

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

    @Transient
    public void setCoordinateName() {
        this.name =
                "X" + getPlanetFieldsByPlanetFieldId().getCoordinateX() +
                        "Y" + getPlanetFieldsByPlanetFieldId().getCoordinateY();
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

    @Transient
    public long getTimeDistanceFromOtherPlanet(PlanetsEntity otherPlanet) {
        long oneFieldTime = MILLISECONDS_PER_FIELD;
        double a = Math.abs(getPlanetFieldsByPlanetFieldId().getCoordinateX() - otherPlanet.getPlanetFieldsByPlanetFieldId().getCoordinateX());
        double b = Math.abs(getPlanetFieldsByPlanetFieldId().getCoordinateY() - otherPlanet.getPlanetFieldsByPlanetFieldId().getCoordinateY());
        double d = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)); //obliczyłem przekątną prostokąta odległości między planetami
        return (long) (d * oneFieldTime);
    }

    @Transient
    public PlanetsEntity getLastHelpedPlanet() {
        List<AllianceHistoriesEntity> allianceHistories = new ArrayList<>(getUsersByUserId().getAllianceHistoriesByUserId());
        Collections.sort(allianceHistories, (o1, o2) -> o2.getTime().compareTo(o1.getTime()));
        return allianceHistories.get(0).getPlanetsByHelpedPlanetId();
    }

    @Transient
    public PlanetsEntity getLastAttackedPlanet() {
        List<AttackHistoriesEntity> historiesEntities = new ArrayList<>(getUsersByUserId().getAttackHistoriesByUserId());
        Collections.sort(historiesEntities, (o1, o2) -> o2.getTime().compareTo(o1.getTime()));
        return historiesEntities.get(0).getPlanetsByAttackedPlanetId();
    }

    public enum FleetStatus {
        ON_THE_MOTHER_PLANET,
        ON_THE_WAY_TO_ATTACK,
        COMING_BACK_FROM_ATTACK,
        ON_THE_WAY_TO_HELP,
        COMING_BACK_FROM_HELP,
        ON_THE_OTHER_PLANET
    }

    @Transient
    public FleetStatus getFleetStatus() {
        CurrentAlliancesEntity currentAlliancesEntity = getCurrentAlliances();
        CurrentAttacksEntity currentAttacksEntity = getCurrentAttacks();
        if (currentAttacksEntity.getTimeOfSendingAttack() != null) {
            if (currentAttacksEntity.getPlanetsByAttackedPlanetId() != this) {
                return FleetStatus.ON_THE_WAY_TO_ATTACK;
            } else {
                return FleetStatus.COMING_BACK_FROM_ATTACK;
            }
        }
        if (currentAlliancesEntity.getTimeOfSendingAlliance() != null) {
            if (currentAlliancesEntity.getPlanetsByHelpedPlanetId() != this) {
                return FleetStatus.ON_THE_WAY_TO_HELP;
            } else {
                return FleetStatus.COMING_BACK_FROM_HELP;
            }
        }
        if (currentAlliancesEntity.getPlanetsByHelpedPlanetId() != null) {
            return FleetStatus.ON_THE_OTHER_PLANET;
        }
        return FleetStatus.ON_THE_MOTHER_PLANET;
    }

    @Transient
    public MyFleetData getMyFleetData() {
        MyFleetData fleetData = new MyFleetData();
        FleetsEntity fleetsEntity = getFleetsByFleetId();
        fleetData.setStatus(getFleetStatus().ordinal());
        ArrayList<ShipsData> ships = getShips(fleetsEntity);
        fleetData.setShips(ships);
        return fleetData;
    }

    @Transient
    private ArrayList<ShipsData> getShips(FleetsEntity fleetsEntity) {
        ArrayList<ShipsData> ships = new ArrayList<>();
        ShipsData warships = new ShipsData();
        ShipsData bombers = new ShipsData();
        ShipsData ironclads = new ShipsData();
        warships.setTypeId(FleetsEntity.FleetType.WARSHIP.ordinal());
        bombers.setTypeId(FleetsEntity.FleetType.BOMBER.ordinal());
        ironclads.setTypeId(FleetsEntity.FleetType.IRONCLADS.ordinal());
        warships.setNumber(fleetsEntity.getWarships());
        bombers.setNumber(fleetsEntity.getBombers());
        ironclads.setNumber(fleetsEntity.getIronclads());
        warships.setBuildInUnuntiumCost(FleetCosts.getWarshipCost(getHangar()));
        bombers.setBuildInUnuntiumCost(FleetCosts.getBomberCost(getHangar()));
        ironclads.setBuildInUnuntiumCost(FleetCosts.getIroncladsCost(getHangar()));
        ships.add(warships);
        ships.add(bombers);
        ships.add(ironclads);
        return ships;
    }
}

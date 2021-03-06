package pl.edu.pw.ee.spacegame.server.entity;

import pl.edu.pw.ee.spacegame.server.controller.building.MyBuildingData;
import pl.edu.pw.ee.spacegame.server.game.BuildingCosts;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity.ID.*;
import static pl.edu.pw.ee.spacegame.server.game.GameBalanceSettings.*;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "users", schema = "", catalog = "spacegame")
public class UsersEntity {
    private Integer userId;
    private String nickname;
    private String salt;
    private String email;
    private String password;
    private Boolean isActivated = false;
    private Collection<ActivationsEntity> activationsesByUserId;
    private Collection<AllianceHistoriesEntity> allianceHistoriesByUserId;
    private Collection<AttackHistoriesEntity> attackHistoriesByUserId;
    private Collection<PlanetsEntity> planetsesByUserId;

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "is_activated")
    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (salt != null ? !salt.equals(that.salt) : that.salt != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return !(isActivated != null ? !isActivated.equals(that.isActivated) : that.isActivated != null);

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (isActivated != null ? isActivated.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<ActivationsEntity> getActivationsesByUserId() {
        return activationsesByUserId;
    }

    public void setActivationsesByUserId(Collection<ActivationsEntity> activationsesByUserId) {
        this.activationsesByUserId = activationsesByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<AllianceHistoriesEntity> getAllianceHistoriesByUserId() {
        return allianceHistoriesByUserId;
    }

    public void setAllianceHistoriesByUserId(Collection<AllianceHistoriesEntity> allianceHistoriesByUserId) {
        this.allianceHistoriesByUserId = allianceHistoriesByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<AttackHistoriesEntity> getAttackHistoriesByUserId() {
        return attackHistoriesByUserId;
    }

    public void setAttackHistoriesByUserId(Collection<AttackHistoriesEntity> attackHistoriesByUserId) {
        this.attackHistoriesByUserId = attackHistoriesByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<PlanetsEntity> getPlanetsesByUserId() {
        return planetsesByUserId;
    }

    public void setPlanetsesByUserId(Collection<PlanetsEntity> planetsesByUserId) {
        this.planetsesByUserId = planetsesByUserId;
    }

    @Transient
    public PlanetsEntity getPlanet() {
        for (PlanetsEntity planet : planetsesByUserId) {
            return planet;
        }
        return null;
    }

    @Transient
    public ArrayList<MyBuildingData> getMyBuildingsData() {
        ArrayList<MyBuildingData> myBuildings = new ArrayList<>();
        myBuildings.add(getUnuntriumMine());
        myBuildings.add(getGadolinMine());
        myBuildings.add(getHangar());
        myBuildings.add(getDefenceSystems());
        return myBuildings;
    }

    @Transient
    private MyBuildingData getUnuntriumMine() {
        BuildingsEntity buildingsEntity = getPlanet().getUnuntriumMine();
        MyBuildingData myBuildingData = new MyBuildingData();
        myBuildingData.setLevel(buildingsEntity.getLevel());
        myBuildingData.setMaxLevel(UNUNTRIUM_MINE_MAX_LEVEL);
        myBuildingData.setTypeId(UNUNTRIUM_MINE_ID.ordinal());
        myBuildingData.setNextLevelInGadolinsCost(BuildingCosts.getUnuntriumMineCost(buildingsEntity));
        return myBuildingData;
    }

    @Transient
    private MyBuildingData getGadolinMine() {
        BuildingsEntity buildingsEntity = getPlanet().getGadolinMine();
        MyBuildingData myBuildingData = new MyBuildingData();
        myBuildingData.setLevel(buildingsEntity.getLevel());
        myBuildingData.setMaxLevel(GADOLIN_MINE_MAX_LEVEL);
        myBuildingData.setTypeId(GADOLIN_MINE_ID.ordinal());
        myBuildingData.setNextLevelInGadolinsCost(BuildingCosts.getGadolinMineCost(buildingsEntity));
        return myBuildingData;
    }

    @Transient
    private MyBuildingData getHangar() {
        BuildingsEntity buildingsEntity = getPlanet().getHangar();
        MyBuildingData myBuildingData = new MyBuildingData();
        myBuildingData.setLevel(buildingsEntity.getLevel());
        myBuildingData.setMaxLevel(HANGAR_MAX_LEVEL);
        myBuildingData.setTypeId(HANGAR_ID.ordinal());
        myBuildingData.setNextLevelInGadolinsCost(BuildingCosts.getHangarCost(buildingsEntity));
        return myBuildingData;
    }

    @Transient
    private MyBuildingData getDefenceSystems() {
        BuildingsEntity buildingsEntity = getPlanet().getDefenceSystems();
        MyBuildingData myBuildingData = new MyBuildingData();
        myBuildingData.setLevel(buildingsEntity.getLevel());
        myBuildingData.setMaxLevel(DEFENCE_SYSTEMS_MAX_LEVEL);
        myBuildingData.setTypeId(DEFENCE_SYSTEMS_ID.ordinal());
        myBuildingData.setNextLevelInGadolinsCost(BuildingCosts.getDefenceSystemsCost(buildingsEntity));
        return myBuildingData;
    }
}

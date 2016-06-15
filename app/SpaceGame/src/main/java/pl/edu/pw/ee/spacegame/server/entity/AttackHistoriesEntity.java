package pl.edu.pw.ee.spacegame.server.entity;

import pl.edu.pw.ee.spacegame.server.controller.history.AttackHistoryData;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "attack_histories", schema = "", catalog = "spacegame")
public class AttackHistoriesEntity {
    private Integer attackHistoryId;
    private Integer warships;
    private Integer bombers;
    private Integer ironclads;
    private Integer gadolin;
    private Integer ununtrium;
    private PlanetsEntity planetsByAttackedPlanetId;
    private Byte result;
    private UsersEntity usersByUserId;
    private Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());

    @Id
    @GeneratedValue
    @Column(name = "attack_history_id")
    public Integer getAttackHistoryId() {
        return attackHistoryId;
    }

    public void setAttackHistoryId(Integer attackHistoryId) {
        this.attackHistoryId = attackHistoryId;
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

    @Basic
    @Column(name = "result")
    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
    @Column(name = "gadolin")
    public Integer getGadolin() {
        return gadolin;
    }

    public void setGadolin(Integer gadolin) {
        this.gadolin = gadolin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttackHistoriesEntity that = (AttackHistoriesEntity) o;

        if (attackHistoryId != null ? !attackHistoryId.equals(that.attackHistoryId) : that.attackHistoryId != null)
            return false;
        if (warships != null ? !warships.equals(that.warships) : that.warships != null) return false;
        if (bombers != null ? !bombers.equals(that.bombers) : that.bombers != null) return false;
        if (ironclads != null ? !ironclads.equals(that.ironclads) : that.ironclads != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        return !(result != null ? !result.equals(that.result) : that.result != null);

    }

    @Override
    public int hashCode() {
        int result1 = attackHistoryId != null ? attackHistoryId.hashCode() : 0;
        result1 = 31 * result1 + (warships != null ? warships.hashCode() : 0);
        result1 = 31 * result1 + (bombers != null ? bombers.hashCode() : 0);
        result1 = 31 * result1 + (ironclads != null ? ironclads.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (time != null ? time.hashCode() : 0);
        return result1;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "attacked_planet_id", referencedColumnName = "planet_id")
    public PlanetsEntity getPlanetsByAttackedPlanetId() {
        return planetsByAttackedPlanetId;
    }

    public void setPlanetsByAttackedPlanetId(PlanetsEntity planetsByAttackedPlanetId) {
        this.planetsByAttackedPlanetId = planetsByAttackedPlanetId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @Transient
    public AttackHistoryData getAttackHistoryData() {
        AttackHistoryData attackHistoryData = new AttackHistoryData();
        attackHistoryData.setAttackerNickname(usersByUserId.getNickname());
        attackHistoryData.setResult(result);
        attackHistoryData.setAttackedPlanetName(getPlanetsByAttackedPlanetId().getName());
        attackHistoryData.setTime(time);
        attackHistoryData.setBombers(bombers);
        attackHistoryData.setIronclads(ironclads);
        attackHistoryData.setWarships(warships);
        return attackHistoryData;
    }
}

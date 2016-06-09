package pl.edu.pw.ee.spacegame.server.entity;

import pl.edu.pw.ee.spacegame.server.controller.history.AttackHistoryData;

import javax.persistence.*;

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
    private Integer attackedPlanetId;
    private Byte result;
    private UsersEntity usersByUserId;

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
    @Column(name = "attacked_planet_id")
    public Integer getAttackedPlanetId() {
        return attackedPlanetId;
    }

    public void setAttackedPlanetId(Integer attackedPlanetId) {
        this.attackedPlanetId = attackedPlanetId;
    }

    @Basic
    @Column(name = "result")
    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
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
        if (attackedPlanetId != null ? !attackedPlanetId.equals(that.attackedPlanetId) : that.attackedPlanetId != null)
            return false;
        return !(result != null ? !result.equals(that.result) : that.result != null);

    }

    @Override
    public int hashCode() {
        int result1 = attackHistoryId != null ? attackHistoryId.hashCode() : 0;
        result1 = 31 * result1 + (warships != null ? warships.hashCode() : 0);
        result1 = 31 * result1 + (bombers != null ? bombers.hashCode() : 0);
        result1 = 31 * result1 + (ironclads != null ? ironclads.hashCode() : 0);
        result1 = 31 * result1 + (attackedPlanetId != null ? attackedPlanetId.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @Transient
    public AttackHistoryData getAttackHistoryData() {
        AttackHistoryData attackHistoryData = new AttackHistoryData();
        attackHistoryData.setResult(result);
        //attackHistoryData.setAttackedPlanetX();
        //attackHistoryData.setAttackedPlanetY();
        attackHistoryData.setBombers(bombers);
        attackHistoryData.setIronclads(ironclads);
        attackHistoryData.setWarships(warships);
        return attackHistoryData;
    }
}

package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;

/**
 * Created by Michał on 2016-06-04.
 */
@Entity
@Table(name = "alliance_histories", schema = "", catalog = "spacegame")
public class AllianceHistoriesEntity {
    private Integer allianceHistoryId;
    private Integer warships;
    private Integer bombers;
    private Integer ironclads;
    private Byte result;
    private UsersEntity usersByUserId;
    private PlanetsEntity planetsByHelpedPlanetId;

    @Id
    @GeneratedValue
    @Column(name = "alliance_history_id")
    public Integer getAllianceHistoryId() {
        return allianceHistoryId;
    }

    public void setAllianceHistoryId(Integer allianceHistoryId) {
        this.allianceHistoryId = allianceHistoryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllianceHistoriesEntity that = (AllianceHistoriesEntity) o;

        if (allianceHistoryId != null ? !allianceHistoryId.equals(that.allianceHistoryId) : that.allianceHistoryId != null)
            return false;
        if (warships != null ? !warships.equals(that.warships) : that.warships != null) return false;
        if (bombers != null ? !bombers.equals(that.bombers) : that.bombers != null) return false;
        if (ironclads != null ? !ironclads.equals(that.ironclads) : that.ironclads != null) return false;
        return !(result != null ? !result.equals(that.result) : that.result != null);

    }

    @Override
    public int hashCode() {
        int result1 = allianceHistoryId != null ? allianceHistoryId.hashCode() : 0;
        result1 = 31 * result1 + (warships != null ? warships.hashCode() : 0);
        result1 = 31 * result1 + (bombers != null ? bombers.hashCode() : 0);
        result1 = 31 * result1 + (ironclads != null ? ironclads.hashCode() : 0);
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

    @ManyToOne
    @JoinColumn(name = "helped_planet_id", referencedColumnName = "planet_id")
    public PlanetsEntity getPlanetsByHelpedPlanetId() {
        return planetsByHelpedPlanetId;
    }

    public void setPlanetsByHelpedPlanetId(PlanetsEntity planetsByHelpedPlanetId) {
        this.planetsByHelpedPlanetId = planetsByHelpedPlanetId;
    }
}

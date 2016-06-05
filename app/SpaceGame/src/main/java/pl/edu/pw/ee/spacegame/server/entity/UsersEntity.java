package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Micha³ on 2016-06-04.
 */
@Entity
@Table(name = "users", schema = "", catalog = "spacegame")
public class UsersEntity {
    private Integer userId;
    private String nickname;
    private String salt;
    private String email;
    private String password;
    private Byte isActivated = 0;
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
    public Byte getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Byte isActivated) {
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
}

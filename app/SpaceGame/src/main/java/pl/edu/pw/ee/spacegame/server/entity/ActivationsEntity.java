package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;

/**
 * Created by Micha³ on 2016-06-04.
 */
@Entity
@Table(name = "activations", schema = "", catalog = "spacegame")
public class ActivationsEntity {
    private Integer activationId;
    private String activationCode;
    private UsersEntity usersByUserId;
    private PlanetFieldsEntity planetFieldsByPlanetFieldId;

    @Id
    @GeneratedValue
    @Column(name = "activation_id")
    public Integer getActivationId() {
        return activationId;
    }

    public void setActivationId(Integer activationId) {
        this.activationId = activationId;
    }


    @Basic
    @Column(name = "activation_code")
    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivationsEntity that = (ActivationsEntity) o;

        if (activationId != null ? !activationId.equals(that.activationId) : that.activationId != null) return false;
        return !(activationCode != null ? !activationCode.equals(that.activationCode) : that.activationCode != null);

    }

    @Override
    public int hashCode() {
        int result = activationId != null ? activationId.hashCode() : 0;
        result = 31 * result + (activationCode != null ? activationCode.hashCode() : 0);
        return result;
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
    @JoinColumn(name = "planet_field_id", referencedColumnName = "planet_field_id", nullable = false)
    public PlanetFieldsEntity getPlanetFieldsByPlanetFieldId() {
        return planetFieldsByPlanetFieldId;
    }

    public void setPlanetFieldsByPlanetFieldId(PlanetFieldsEntity planetFieldsByPlanetFieldId) {
        this.planetFieldsByPlanetFieldId = planetFieldsByPlanetFieldId;
    }
}

package pl.edu.pw.ee.spacegame.server.controller.fleets;

import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;

/**
 * Created by Michał on 2016-06-10.
 */
public class BuildingShipsData {

    private AuthenticationData authenticationData;
    private Integer typeId;
    private Integer number;

    public AuthenticationData getAuthenticationData() {
        return authenticationData;
    }

    public void setAuthenticationData(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}

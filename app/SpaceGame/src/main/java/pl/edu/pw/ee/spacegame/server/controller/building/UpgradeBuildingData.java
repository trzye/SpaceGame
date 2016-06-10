package pl.edu.pw.ee.spacegame.server.controller.building;

import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;

/**
 * Created by Michał on 2016-06-10.
 */
public class UpgradeBuildingData {

    private AuthenticationData authenticationData;
    private Integer typeId;

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
}

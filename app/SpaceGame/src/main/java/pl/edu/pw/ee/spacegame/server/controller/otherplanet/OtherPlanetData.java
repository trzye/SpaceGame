package pl.edu.pw.ee.spacegame.server.controller.otherplanet;

import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;

/**
 * Created by KTamo_000 on 2016-06-14.
 */
public class OtherPlanetData {
    private AuthenticationData authenticationData;
    private int coordinateX;
    private int coordinateY;

    public AuthenticationData getAuthenticationData() {
        return authenticationData;
    }

    public void setAuthenticationData(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
}

package pl.edu.pw.ee.spacegame.server.controller.signup;

/**
 * Created by Michał on 2016-06-05.
 */
public class SignUpData {
    private String rawPassword;
    private String nickname;
    private String email;
    private Integer coordinateX;
    private Integer coordinateY;

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public Integer getCoordinateX() {
        return coordinateX;
    }

    public Integer getCoordinateY() {
        return coordinateY;
    }
}

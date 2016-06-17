package pl.edu.pw.ee.spacegame.server.controller.attacksandalliances;

/**
 * Created by Michał on 2016-06-16.
 */
public class IncomingData {
    private Boolean isAttack;
    private Long secondsToCome;
    private Integer coordinateX;
    private Integer coordinateY;
    private String nickname;

    public Boolean getIsAttack() {
        return isAttack;
    }

    public void setIsAttack(Boolean isAttack) {
        this.isAttack = isAttack;
    }

    public Long getSecondsToCome() {
        return secondsToCome;
    }

    public void setSecondsToCome(Long secondsToCome) {
        this.secondsToCome = secondsToCome;
    }

    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

package pl.edu.pw.ee.spacegame.server.controller.attacksandalliances;

/**
 * Created by Michał on 2016-06-16.
 */
public class OutgoingData {
    private Integer status;
    private Long secondsToCome;
    private Long secondsOfWholeOperation;
    private Integer coordinateX;
    private Integer coordinateY;
    private String nickname;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getSecondsOfWholeOperation() {
        return secondsOfWholeOperation;
    }

    public void setSecondsOfWholeOperation(Long secondsOfWholeOperation) {
        this.secondsOfWholeOperation = secondsOfWholeOperation;
    }
}

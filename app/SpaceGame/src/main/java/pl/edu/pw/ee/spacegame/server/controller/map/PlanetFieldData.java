package pl.edu.pw.ee.spacegame.server.controller.map;

import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;

/**
 * Created by Michał on 2016-06-06.
 */
public class PlanetFieldData {

    private Integer coordinateX;
    private Integer coordinateY;
    private String status;

    public PlanetFieldData(PlanetFieldsEntity planetFieldsEntity) {
        coordinateX = planetFieldsEntity.getCoordinateX();
        coordinateY = planetFieldsEntity.getCoordinateY();
        status = planetFieldsEntity.getStatus().name();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

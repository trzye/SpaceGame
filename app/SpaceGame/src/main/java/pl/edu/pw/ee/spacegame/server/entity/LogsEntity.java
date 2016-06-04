package pl.edu.pw.ee.spacegame.server.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Micha³ on 2016-06-04.
 */
@Entity
@Table(name = "logs", schema = "", catalog = "spacegame")
public class LogsEntity {
    private Integer logId;
    private Timestamp time;
    private String clazz;
    private String information;
    private String level;

    @Id
    @GeneratedValue
    @Column(name = "log_id")
    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "class")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Basic
    @Column(name = "information")
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Basic
    @Column(name = "level")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogsEntity that = (LogsEntity) o;

        if (logId != null ? !logId.equals(that.logId) : that.logId != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) return false;
        if (information != null ? !information.equals(that.information) : that.information != null) return false;
        return !(level != null ? !level.equals(that.level) : that.level != null);

    }

    @Override
    public int hashCode() {
        int result = logId != null ? logId.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        result = 31 * result + (information != null ? information.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }
}

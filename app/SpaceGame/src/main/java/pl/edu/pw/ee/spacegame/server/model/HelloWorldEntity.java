package pl.edu.pw.ee.spacegame.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Michał on 2016-05-05.
 */
@Entity
@Table(name = "hello_world")
public class HelloWorldEntity {

    @Id
    @Column(name = "name")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HelloWorldEntity{" +
                "name='" + this.name + '\'' +
                '}';
    }
}

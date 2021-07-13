package com.teststation.crudrabbitmongotest.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "leaguePlayers")
public class Player {

    @Id
    private String id;

    @Indexed
    private String name;

    private String country;

    private int age;

    private Date creationDate;

    public Player(String name, String country, int age) {
        this.name = name;
        this.country = country;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", age=" + age +
                ", creationDate=" + creationDate +
                '}';
    }
}

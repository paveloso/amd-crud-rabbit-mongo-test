package com.teststation.crudrabbitmongotest.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PlayerDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private String country;

    @JsonProperty("age")
    private int age;

    public PlayerDto(@JsonProperty("name") String name, @JsonProperty("country") String country, @JsonProperty("age") int age) {
        this.name = name;
        this.country = country;
        this.age = age;
    }
}

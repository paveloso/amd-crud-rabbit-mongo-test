package com.teststation.crudrabbitmongotest.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    @JsonProperty("status")
    private Status status;

    @JsonProperty("message")
    private String message;

    private List<T> data;

    public ResponseDto(@JsonProperty("status") Status status, @JsonProperty("message") String message) {
        this.status = status;
        this.message = message;
    }

    public enum Status {
        SUCCESS, FAIL
    }
}

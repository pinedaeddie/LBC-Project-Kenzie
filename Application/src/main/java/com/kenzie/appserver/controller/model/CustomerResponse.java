package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.Order;
import javax.validation.constraints.NotEmpty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    @NotEmpty
    @JsonProperty("id")
    private String id;
    @NotEmpty
    @JsonProperty("userName")
    private String userName;


    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}



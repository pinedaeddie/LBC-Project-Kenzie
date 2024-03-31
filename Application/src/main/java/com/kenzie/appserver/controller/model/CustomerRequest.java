package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CustomerRequest {
    @NotNull
    @JsonProperty("userName")
    private String userName;

    public String getUserName() {
        return userName;
    }

}

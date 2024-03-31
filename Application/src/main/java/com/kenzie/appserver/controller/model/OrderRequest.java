package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @NotNull
    @JsonProperty("name")
    private String name;
    @NotEmpty
    @JsonProperty("items")
    private List<String> items;

    public String getName() {
        return name;
    }

    public List<String> items() {
        return items;
    }

}

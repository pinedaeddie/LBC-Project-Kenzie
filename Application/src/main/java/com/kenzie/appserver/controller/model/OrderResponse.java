package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

    @NotEmpty
    @JsonProperty("id")
    private String id;
    @NotEmpty
    @JsonProperty("name")
    private String name;
    @NotEmpty
    @JsonProperty("items")
    private List<String> items;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}

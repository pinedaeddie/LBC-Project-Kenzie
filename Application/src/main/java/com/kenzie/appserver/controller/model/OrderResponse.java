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
    @JsonProperty("userName")
    private String userName;
    @NotEmpty
    @JsonProperty("items")
    private List<String> items;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getItems() {return items;}
    public void setItems(List<String> items) {this.items = items;}
}

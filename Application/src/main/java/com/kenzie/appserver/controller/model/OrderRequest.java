package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @NotNull
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("items")
    private List<String> items;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {this.userName = userName;}

    public List<String> getItems() {return items;}

    public void setItems(List<String> items) {this.items = items;}
}

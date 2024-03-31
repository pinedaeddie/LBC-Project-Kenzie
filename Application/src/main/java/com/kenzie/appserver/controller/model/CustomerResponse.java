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
    @JsonProperty("name")
    private String name;
    @JsonProperty("incompleteOrder")
    private List<Order> incompleteOrder;
    @JsonProperty("completeOrder")
    private List<Order> completeOrder;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setIncompleteOrder(List<Order> incompleteOrder) {
        this.incompleteOrder = incompleteOrder;
    }

    public void setCompleteOrder(List<Order> completeOrder) {
        this.completeOrder = completeOrder;
    }
    //    @NotEmpty
//    @JsonProperty("itemId")
//    private String itemId;
//
//    public String getItemId() {
//        return itemId;
//    }
//
//    public void setItemId(String itemId) {
//        this.itemId = itemId;
//    }
//
//
//    @NotEmpty
//    @JsonProperty("name")
//    private String name;
//    @NotEmpty
//    @JsonProperty("quantity")
//    private Integer quantity;
//    @JsonProperty("price")
//    private Double price;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
}



package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CustomerRequest {
    @NotNull
    @JsonProperty("name")
    private String name;
//
    public String getName() {
        return name;
    }

}
//@NotNull
//    @JsonProperty("quantity")
//    private Integer quantity;
//    @NotNull
//    @JsonProperty("price")
//    private Double price;

//    public String getItemId() {
//        return itemId;
//    }
//
//    public String setItemId(String itemId) {
//        return this.itemId = UUID.randomUUID().toString();
//    }

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
//}

package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;
import java.util.List;

@DynamoDBTable(tableName = "Orders")
public class OrderRecord {

    private String id;
    private String name;
    private Date orderDate;
    private List<String> items;
    private double orderTotal;

    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBHashKey(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "OrderDate")
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @DynamoDBAttribute(attributeName = "items")
    public List<String> getOrderItems() {
        return items;
    }

    public void setOrderItems(List<String> items) {
        this.items = items;
    }

    @DynamoDBAttribute(attributeName = "OrderTotal")
    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }



}

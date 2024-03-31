package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.kenzie.appserver.service.model.Order;

import java.util.List;

@DynamoDBTable(tableName = "Customers")
public class CustomerRecord {
    private String id;
    private String name;
    private List<Order> completedOrders;
    private List<Order> incompleteOrders;
    @DynamoDBHashKey (attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @DynamoDBAttribute(attributeName = "completedOrders")
    public List<Order> getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(List<Order> completedOrders) {
        this.completedOrders = completedOrders;
    }
    @DynamoDBAttribute(attributeName = "incompleteOrders")
    public List<Order> getIncompleteOrders() {
        return incompleteOrders;
    }

    public void setIncompleteOrders(List<Order> incompleteOrders) {
        this.incompleteOrders = incompleteOrders;
    }
}

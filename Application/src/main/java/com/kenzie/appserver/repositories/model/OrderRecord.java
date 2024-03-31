package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Orders")
public class OrderRecord {

    private String id;
    private String name;
    private Date orderDate;
    private List<String> items;
    private double orderTotal;

    @DynamoDBHashKey(attributeName = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "OrderDate")
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @DynamoDBAttribute(attributeName = "items")
    public List<String> getItems() {
        return items;
    }
    public void setItems(List<String> items) {
        this.items = items;
    }

    @DynamoDBAttribute(attributeName = "OrderTotal")
    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderRecord)) return false;
        OrderRecord that = (OrderRecord) o;
        return Double.compare(that.orderTotal, orderTotal) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(orderDate, that.orderDate) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, orderDate, items, orderTotal);
    }
}

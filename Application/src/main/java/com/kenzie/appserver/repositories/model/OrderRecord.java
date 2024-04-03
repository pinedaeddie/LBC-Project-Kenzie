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
    private String userName;
    private Date orderDate;
    private List<String> items;

    @DynamoDBHashKey(attributeName = "userName")
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DynamoDBAttribute(attributeName = "id")
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    @DynamoDBAttribute(attributeName = "orderDate")
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

    @Override
    public String toString() {
        return "OrderRecord{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", orderDate=" + orderDate +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderRecord)) return false;
        OrderRecord that = (OrderRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(userName, that.userName) && Objects.equals(orderDate, that.orderDate) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, orderDate, items);
    }
}

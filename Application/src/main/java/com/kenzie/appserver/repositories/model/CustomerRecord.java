package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.kenzie.appserver.service.model.Order;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Customers")
public class CustomerRecord {
    private String id;
    private String userName;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "userName")
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerRecord)) return false;
        CustomerRecord that = (CustomerRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }
}

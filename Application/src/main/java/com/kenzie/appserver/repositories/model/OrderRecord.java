package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Products")
public class OrderRecord {

    private String itemId;
    private String itemName;
    private double itemCost;

    @DynamoDBHashKey(attributeName = "ItemId")
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @DynamoDBAttribute(attributeName = "ItemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @DynamoDBAttribute(attributeName = "ItemCost")
    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderRecord)) return false;
        OrderRecord that = (OrderRecord) o;
        return Double.compare(that.itemCost, itemCost) == 0 && Objects.equals(itemId, that.itemId) && Objects.equals(itemName, that.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, itemCost);
    }
}

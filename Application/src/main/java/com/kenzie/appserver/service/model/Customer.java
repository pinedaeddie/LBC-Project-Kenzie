package com.kenzie.appserver.service.model;

import java.util.List;

public class Customer {

    private final String id;
    private final String userName;

    public Customer(String id, String userName) {

        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
}




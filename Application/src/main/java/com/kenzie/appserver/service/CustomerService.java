package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CustomerRequest;
import com.kenzie.appserver.repositories.CustomerRepository;
import com.kenzie.appserver.repositories.model.CustomerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService (CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }


    public CustomerRecord startOrder(CustomerRequest customerRequest) {

        CustomerRecord record = new CustomerRecord();
        record.setUserName(customerRequest.getUserName());
        record.setId(UUID.randomUUID().toString());
        customerRepository.save(record);
        return record;
    }
}

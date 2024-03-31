package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.CustomerRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface CustomerRepository extends CrudRepository<CustomerRecord, String> {

    Optional<CustomerRecord> findById(String orderId);
}


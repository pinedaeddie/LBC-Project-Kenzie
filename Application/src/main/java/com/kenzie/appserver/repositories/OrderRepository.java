package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.OrderRecord;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface OrderRepository extends CrudRepository<OrderRecord, String> {

    Optional<OrderRecord> findById(String orderId);
}

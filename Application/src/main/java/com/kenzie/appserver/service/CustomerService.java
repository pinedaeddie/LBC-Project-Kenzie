package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CustomerRequest;
import com.kenzie.appserver.controller.model.OrderRequest;
import com.kenzie.appserver.controller.model.OrderResponse;
import com.kenzie.appserver.repositories.CustomerRepository;
import com.kenzie.appserver.repositories.model.CustomerRecord;
import com.kenzie.appserver.service.model.Customer;
import com.kenzie.appserver.service.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repo;

    public CustomerService (CustomerRepository repo){
        this.repo =repo;
    }
    public CustomerRecord startOrder(CustomerRequest request) {

        CustomerRecord record = new CustomerRecord();
        record.setName(request.getName());
        record.setId(UUID.randomUUID().toString());
        record.setIncompleteOrders(new ArrayList<>());
        record.setCompletedOrders((new ArrayList<>()));
        repo.save(record);
        return record;
    }
    public void addToOrder (Order order){
        Optional<CustomerRecord> record = repo.findById(order.getName());

        List<Order> incompleteOrders = record.get().getIncompleteOrders();
        incompleteOrders.add(order);

    }

    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();
        repo
                .findAll()
                .forEach(customer -> customers.add(new Customer(customer.getId(), customer.getName(), customer.getCompletedOrders(), customer.getIncompleteOrders())));
        return customers;
//        if (item == null) {
//            throw new IllegalArgumentException("Item is null!");
//        }
//        Optional<OrderRecord> record = repo.findById(id);
//        if (!record.isPresent()) {
//            throw new IllegalArgumentException("Order record not found: " + id);
//        }
//        OrderRecord orderRecord = record.get();
//        List<String> newItem = orderRecord.getOrderItems();
//        newItem.add(item);
//        orderRecord.setOrderItems(newItem);
//        repo.save(orderRecord);
    }

    public void removeItemFromOrder(String itemId, String orderId) {
//
//        try {
//            orderService.removeItemFromOrder(itemId,orderId);
//            return ResponseEntity.ok("Item was successfully removed from Order");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
    }
}

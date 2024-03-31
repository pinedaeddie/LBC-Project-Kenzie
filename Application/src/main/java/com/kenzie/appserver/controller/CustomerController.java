package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CustomerRequest;
import com.kenzie.appserver.controller.model.CustomerResponse;
import com.kenzie.appserver.controller.model.OrderRequest;
import com.kenzie.appserver.controller.model.OrderResponse;
import com.kenzie.appserver.repositories.model.CustomerRecord;
import com.kenzie.appserver.service.CustomerService;
import com.kenzie.appserver.service.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService service;

    public CustomerController (CustomerService service){
        this.service = service;
    }
    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> findAllCustomer(){
        List<Customer> customers = service.findAll();

        List<CustomerResponse> responses = customers.stream().map(customer -> customerToResponse(customer)).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
    @PostMapping
    public ResponseEntity<CustomerResponse> startOrder(@RequestBody CustomerRequest customerRequest) {
        try {
            CustomerResponse response = new CustomerResponse();
            CustomerRecord record = service.startOrder(customerRequest);
            response.setName(customerRequest.getName());
            response.setId(record.getId());
            response.setCompleteOrder(record.getCompletedOrders());
            response.setIncompleteOrder(record.getIncompleteOrders());
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
//    @PutMapping
//    public ResponseEntity<CustomerResponse> completeOrder(@RequestBody OrderRequest orderRequest) {
//        try {
//            CustomerResponse response = new CustomerResponse();
//            CustomerRecord record = service.addToOrder(orderRequest.getName(), orderRequest.getOrderItems());
//            response.setName(orderRequest.getName());
//            response.setId(record.getId());
//            response.setCompleteOrder(record.getCompletedOrders());
////            response.setIncompleteOrder(record.getIncompleteOrders());
//            return ResponseEntity.ok().body(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
    private CustomerResponse customerToResponse(Customer customer){
        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setCompleteOrder(customer.getCompletedOrder());
//        response.setIncompleteOrder(customer.getIncompleteOrder());

        return response;

    }

//    @PostMapping
//    public ResponseEntity<CustomerResponse> completeOrder(@RequestBody CustomerRequest customerRequest){
//        return ResponseEntity.ok(null);
//    }
//    @GetMapping("/customer/{id}")
//    public ResponseEntity<Customer> findCustomer(@PathVariable("id") String id){
//        return ResponseEntity.ok(null);
//    }
}

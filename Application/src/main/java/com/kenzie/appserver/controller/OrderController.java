package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CustomerRequest;
import com.kenzie.appserver.controller.model.CustomerResponse;
import com.kenzie.appserver.controller.model.OrderRequest;
import com.kenzie.appserver.controller.model.OrderResponse;

import com.kenzie.appserver.repositories.model.CustomerRecord;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.CustomerService;
import com.kenzie.appserver.service.OrderService;
import com.kenzie.appserver.service.model.Customer;
import com.kenzie.appserver.service.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }


    @PostMapping("/start")
    public ResponseEntity<CustomerResponse> startOrder(@RequestBody CustomerRequest customerRequest) {
        try {
            CustomerResponse response = new CustomerResponse();
            CustomerRecord record = customerService.startOrder(customerRequest);
            response.setUserName(customerRequest.getUserName());
            response.setId(record.getId());
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/add-item/{name}")
    public ResponseEntity<OrderRecord> addItemToOrder(@PathVariable("name") String name, @RequestBody  String item) {

        try {
            return ResponseEntity.ok(orderService.addItemToOrder(name, item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderRecord> placeOrder (@RequestBody OrderRequest orderRequest){
        OrderRecord record = new OrderRecord();
        record.setName(orderRequest.getName());
        record.setId(UUID.randomUUID().toString());
        record.setItems(orderRequest.items());
        record.setOrderDate(new Date());
        orderService.placeOrder(record);
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/remove-item/{byName}")
    public ResponseEntity<OrderRecord> removeItemFromOrder(@PathVariable ("byName") String orderId, String item) {
        orderService.removeItemFromOrder(orderId, item);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> findAll() {

        try {
            List<Order> order = orderService.findAll();
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<OrderRecord> searchOrderByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(orderService.searchOrderByName(name));
    }



//    @GetMapping("/total/{orderId}")
//    public ResponseEntity<Double> getOrderTotal(@PathVariable("orderId") String orderId) {
//
//        try {
//            double total = orderService.getTotal(orderId);
//            return ResponseEntity.ok().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}

package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.OrderRequest;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.OrderService;
import com.kenzie.appserver.service.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/startOrder")
    public ResponseEntity<OrderRecord> startOrder (@RequestBody OrderRequest orderRequest) {

        OrderRecord record = new OrderRecord();
        record.setId(UUID.randomUUID().toString());
        record.setUserName(orderRequest.getUserName());
        record.setItems(orderRequest.getItems());
        record.setOrderDate(new Date());
        orderService.startOrder(record);
        return ResponseEntity.ok(record);
    }

    @PostMapping("/add-item/{username}")
    public ResponseEntity<OrderRecord> addItemToOrder(@PathVariable("username") String name, @RequestBody  String item) {

        try {
            return ResponseEntity.ok(orderService.addItemToOrder(name, item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<OrderRecord> searchOrderByName(@PathVariable("username") String name) {
        return ResponseEntity.ok(orderService.searchOrderByName(name));
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

    @DeleteMapping("/remove-item/{username}")
    public ResponseEntity<OrderRecord> removeItemFromOrder(@PathVariable ("username") String orderId, String item) {
        orderService.removeItemFromOrder(orderId, item);
        return ResponseEntity.ok().build();
    }
}

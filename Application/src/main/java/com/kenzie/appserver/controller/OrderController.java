package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ItemRequest;
import com.kenzie.appserver.controller.model.OrderRequest;
import com.kenzie.appserver.controller.model.OrderResponse;

import com.kenzie.appserver.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/start")
    public ResponseEntity<OrderResponse> startOrder(@RequestBody OrderRequest orderRequest) {
        // needs to Implement
        return null;
    }

    @PostMapping("/add-item")
    public ResponseEntity<OrderResponse> addItemToOrder(@RequestBody ItemRequest itemRequest) {
        // needs to Implement
        return null;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("orderId") String orderId) {
        // needs to Implement
        return null;
    }

    @DeleteMapping("/remove-item/{itemId}")
    public ResponseEntity<String> removeItemFromOrder(@PathVariable ("itemId") String itemId) {
        // needs to Implement
        return null;
    }

    @GetMapping("/items/search/{itemId}")
    public ResponseEntity<String> searchItemById(@PathVariable("itemId") String itemId) {
        // needs to Implement
        return null;
    }
}

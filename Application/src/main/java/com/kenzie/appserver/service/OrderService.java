package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void startOrder (OrderRecord order){
        order.setId(order.getId());
        order.setItems(order.getItems());
        order.setOrderDate(new Date());
        orderRepository.save(order);
    }

    public OrderRecord addItemToOrder(String name, List<String> items) {
        Optional<OrderRecord> recordOptional = orderRepository.findById(name);
        if (recordOptional.isPresent()) {
            OrderRecord orderRecord = recordOptional.get();
            List<String> itemList = orderRecord.getItems();
            itemList.addAll(items);
            orderRecord.setItems(itemList);
            orderRepository.save(orderRecord);
            return orderRecord;
        } else {
            throw new IllegalArgumentException("Order record not found: " + name);
        }
    }

    public OrderRecord searchOrderByName (String name) {
        Optional<OrderRecord> record = orderRepository.findById(name);
        if (!record.isPresent()) {
            throw new IllegalArgumentException("Item id is invalid: " + name);
        }
        return record.get();
    }

    public List<Order> findAll() {

        List<Order> order = new ArrayList<>();
        Iterable<OrderRecord> orderRecord = orderRepository.findAll();
        if(!orderRecord.iterator().hasNext()){
            throw new IllegalArgumentException("No Orders Found" );
        }
        for (OrderRecord recordList : orderRecord){
            Order newOrder = new Order(recordList.getId(), recordList.getUserName(), recordList.getOrderDate(), recordList.getItems());
            order.add(newOrder);
        }
        return order;
    }

    public void removeItemFromOrder(String orderId, String item) {
        Optional<OrderRecord> recordOptional = orderRepository.findById(orderId);
        if (recordOptional.isPresent()) {
            OrderRecord orderRecord = recordOptional.get();
            List<String> itemList = orderRecord.getItems();
            itemList.removeIf(product -> product.equals(item));
            orderRecord.setItems(itemList);
            orderRepository.save(orderRecord);

        } else {
            throw new IllegalArgumentException("Order record not found: " + orderId);
        }
    }
}

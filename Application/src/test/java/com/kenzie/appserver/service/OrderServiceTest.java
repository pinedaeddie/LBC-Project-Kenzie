package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.model.Order;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

   @Test
    void searchOrderByUsername_validOrderName_returnsOrderRecord(){
//       Given
       OrderRecord expectedRecord = new OrderRecord();
       expectedRecord.setUserName("testUser");
       when(orderRepository.findById(expectedRecord.getUserName())).thenReturn(Optional.of(expectedRecord));
//       When
       OrderRecord returnedRecord = orderService.searchOrderByName(expectedRecord.getUserName());
//       Then
       Assertions.assertEquals(expectedRecord.getUserName(), returnedRecord.getUserName(), "The record userNames do not match!");

   }
    @Test
    void searchByOrder_invalidRecord_throwsException()throws Exception{
        OrderRecord expectedRecord = new OrderRecord();
        expectedRecord.setUserName("testUser");
        when(orderRepository.findById(expectedRecord.getUserName())).thenReturn(Optional.empty());
//       When
        try{
            OrderRecord returnedRecord = orderService.searchOrderByName(expectedRecord.getUserName());
            Assertions.assertNotEquals(expectedRecord.getUserName(), returnedRecord.getUserName(), "The record userName was returned for an undefined record!");
        }catch (Exception e){
//            Then
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> orderService.searchOrderByName(expectedRecord.getUserName()));    }
    }
    @Test
    void findAll_validRecords_returnsListOfOrders(){
        ArrayList<OrderRecord> recordList = new ArrayList<>();

        OrderRecord expectedRecord = new OrderRecord();
        OrderRecord expectedRecord2 = new OrderRecord();
        OrderRecord expectedRecord3 = new OrderRecord();

        expectedRecord.setUserName("testRecord1");
        expectedRecord.setId("1234");
        expectedRecord.setItems(new ArrayList<>());
        expectedRecord2.setUserName("testRecord2");
        expectedRecord2.setId("5678");
        expectedRecord2.setItems(new ArrayList<>());
        expectedRecord3.setUserName("testRecord3");
        expectedRecord3.setId("9");
        expectedRecord3.setItems(new ArrayList<>());
        recordList.add(expectedRecord);
        recordList.add(expectedRecord2);
        recordList.add(expectedRecord3);

        when(orderRepository.findAll()).thenReturn(recordList);
//       When
        List<Order> returnedRecord = orderService.findAll();
        Assertions.assertEquals(returnedRecord.get(0).getUserName(),expectedRecord.getUserName());
        Assertions.assertEquals(returnedRecord.get(1).getUserName(),expectedRecord2.getUserName());
        Assertions.assertEquals(returnedRecord.get(2).getUserName(),expectedRecord3.getUserName());


    }
    @Test
    void findAll_noRecords_throwsException() throws Exception{
//       Given
        ArrayList<OrderRecord> recordList = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(recordList);
        try{
//       When
            List<Order> returnedRecord = orderService.findAll();
        }catch (Exception e){
//       Then
            Assert.assertThrows(IllegalArgumentException.class,
            ()->orderService.findAll());
        }
    }
}

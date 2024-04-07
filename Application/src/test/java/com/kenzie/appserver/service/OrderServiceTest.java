package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.OrderRepository;
import com.kenzie.appserver.repositories.model.OrderRecord;
import com.kenzie.appserver.service.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
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
     *  orderService.startOrder
     *  ------------------------------------------------------------------------ **/

    @Test
    public void startOrder_NullOrder_ThrowsException() {
        // GIVEN
        OrderRecord nullOrder = null;

        // WHEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.startOrder(nullOrder));

        // THEN
        assertEquals("OrderRecord cannot be null", exception.getMessage());

        // Asserting no interactions with orderRepository
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void startOrder_ValidOrder_RecordSaved() {
        // GIVEN
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setUserName("testUser");
        orderRecord.setItems(Arrays.asList("item1", "item2"));

        // Mocking orderRepository to return an empty Optional
        when(orderRepository.findById(orderRecord.getUserName())).thenReturn(Optional.empty());

        // WHEN
        orderService.startOrder(orderRecord);

        // THEN
        ArgumentCaptor<OrderRecord> captor = ArgumentCaptor.forClass(OrderRecord.class);
        verify(orderRepository).save(captor.capture());
        OrderRecord capturedOrder = captor.getValue();

        assertNotNull(capturedOrder);
        assertEquals("testUser", capturedOrder.getUserName());
        List<String> expectedItems = Arrays.asList("ITEM1", "ITEM2");
        assertEquals(expectedItems.size(), capturedOrder.getItems().size());
        assertTrue(capturedOrder.getItems().containsAll(expectedItems));
        assertNotNull(capturedOrder.getId());
        assertNotNull(capturedOrder.getOrderDate());
    }

    /** ------------------------------------------------------------------------
     *  orderService.addItemToOrder
     *  ------------------------------------------------------------------------ **/

    @Test
    public void addItemToOrder_OrderRecordExists_ItemsAdded() {

        // GIVEN
        String username = "testUser";
        List<String> existingItems = new ArrayList<>();
        existingItems.add("item1");
        existingItems.add("item2");

        OrderRecord existingOrderRecord = new OrderRecord();
        existingOrderRecord.setUserName(username);
        existingOrderRecord.setItems(existingItems);

        when(orderRepository.findById(username)).thenReturn(Optional.of(existingOrderRecord));

        List<String> newItems = new ArrayList<>();
        newItems.add("newItem1");
        newItems.add("newItem2");

        // WHEN
        OrderRecord updatedOrderRecord = orderService.addItemToOrder(username, newItems);

        // THEN
        assertEquals(username, updatedOrderRecord.getUserName());
        List<String> updatedItems = updatedOrderRecord.getItems();

        assertEquals(username, updatedOrderRecord.getUserName());
        assertTrue(existingItems.stream().allMatch(item -> updatedItems.stream().anyMatch(updatedItem -> updatedItem.equalsIgnoreCase(item))));
        assertTrue(newItems.stream().map(String::toUpperCase).allMatch(updatedItems::contains));
        verify(orderRepository, times(1)).findById(username);
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    public void testAddItemToOrder_UserNotFound() {

        // GIVEN
        String userName = "testUser";
        List<String> items = Arrays.asList("item1", "item2");
        when(orderRepository.findById(userName)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> orderService.addItemToOrder(userName, items));
    }


    /** ------------------------------------------------------------------------
     *  orderService.searchOrderByUsername
     *  ------------------------------------------------------------------------ **/

    @Test
    void searchOrderByUsername_validOrderName_returnsOrderRecord() {

       // GIVEN
       OrderRecord expectedRecord = new OrderRecord();
       expectedRecord.setUserName("testUser");
       when(orderRepository.findById(expectedRecord.getUserName())).thenReturn(Optional.of(expectedRecord));

       // WHEN
       OrderRecord returnedRecord = orderService.searchOrderByName(expectedRecord.getUserName());

       // THEN
       assertEquals(expectedRecord.getUserName(), returnedRecord.getUserName(), "The record userNames do not match!");

   }
   @Test
   public void searchByOrder_invalidRecord_throwsException() {

       // GIVEN
       OrderRecord expectedRecord = new OrderRecord();
       expectedRecord.setUserName("testUser");
       when(orderRepository.findById(expectedRecord.getUserName())).thenReturn(Optional.empty());

       // WHEN
       try{
           OrderRecord returnedRecord = orderService.searchOrderByName(expectedRecord.getUserName());
           Assertions.assertNotEquals(expectedRecord.getUserName(), returnedRecord.getUserName(), "The record userName was returned for an undefined record!");
       }catch (Exception e){

           // THEN
           assertThrows(
                   IllegalArgumentException.class,
                   () -> orderService.searchOrderByName(expectedRecord.getUserName()));
       }
   }

    /** ------------------------------------------------------------------------
     *  orderService.findAll
     *  ------------------------------------------------------------------------ **/

   @Test
   public void findAll_validRecords_returnsListOfOrders() {

       // GIVEN
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

       // WHEN & THEN
       List<Order> returnedRecord = orderService.findAll();
       assertEquals(returnedRecord.get(0).getUserName(), expectedRecord.getUserName());
       assertEquals(returnedRecord.get(1).getUserName(), expectedRecord2.getUserName());
       assertEquals(returnedRecord.get(2).getUserName(), expectedRecord3.getUserName());
   }

   @Test
   public void findAll_noRecords_throwsException() {

       // GIVEN
       List<OrderRecord> recordList = new ArrayList<>();
       when(orderRepository.findAll()).thenReturn(recordList);

       // THEN
       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
               () -> orderService.findAll());

       assertEquals("No Orders Found", exception.getMessage());
   }

    /** ------------------------------------------------------------------------
     *  orderService.removeItemFromOrder
     *  ------------------------------------------------------------------------ **/

    @Test
    void removeItemFromOrder_WhenOrderDoesNotExist_ShouldThrowUnsupportedOperationException() {

        // GIVEN
        String username = "testUser";
        String itemToRemove = "ItemToRemove";

        when(orderRepository.findById(username)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UnsupportedOperationException.class, () -> orderService.removeItemFromOrder(username, itemToRemove));
    }

    @Test
    public void removeItemFromOrder_WhenOrderExistsAndItemExists_ShouldRemoveItemFromOrder() {

        // GIVEN
        String orderId = "123";
        String itemToRemove = "ItemToRemove";
        List<String> items = new ArrayList<>();
        items.add("Item1");
        items.add("Item2");
        items.add(itemToRemove);
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(orderId);
        orderRecord.setUserName("TestUser");
        orderRecord.setOrderDate(new Date());
        orderRecord.setItems(items);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderRecord));

        // WHEN
        orderService.removeItemFromOrder(orderId, itemToRemove);

        // THEN
        verify(orderRepository).save(any(OrderRecord.class));
        assertFalse(orderRecord.getItems().contains(itemToRemove));
    }
}
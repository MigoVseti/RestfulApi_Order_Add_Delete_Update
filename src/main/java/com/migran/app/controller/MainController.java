package com.migran.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migran.app.model.Order;
import com.migran.app.repositories.OrderRepositories;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.migran.app.exceptions.GlobalExceptions;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final OrderRepositories orderRepositories;
    private final ObjectMapper objectMapper;

    @PostMapping("/api/new")
    public void newOrder(@RequestBody Order order){
        Order savedOrder = orderRepositories.save(order);
        log.info("New order: " + savedOrder);
    }

    @SneakyThrows
    @GetMapping("/api/orders/all")
    public String getOrders(){
        List<Order> orders = orderRepositories.findAll();
        String ordersJson = objectMapper.writeValueAsString(orders);
        log.info("All orders: " + ordersJson);
        return ordersJson;
    }

    @GetMapping("/api/order")
    public Order getOrderById(@RequestParam int id){
        Optional<Order> orderOptional = orderRepositories.findById(id);
        Order order = orderOptional.orElseThrow(() -> new GlobalExceptions("Order not found with id: " + id));
        log.info("Order with id " + id + " : " + order);
        return order;
    }

    @DeleteMapping("/api/delete")
    public String deleteOrder(@RequestParam int id){
        if (!orderRepositories.existsById(id)){
            throw new GlobalExceptions("Order not found with id: " + id);
        }
        orderRepositories.deleteById(id);
        log.info("Order with id " + id + " deleted");
        return "Order with id " + id + " deleted";
    }

    @PutMapping("/api/order/update")
    public ResponseEntity<Order> updateOrder(@RequestParam int id, @RequestBody Order updatedOrder) {
        return orderRepositories.findById(id)
                .map(order -> {
                    order.setProduct(updatedOrder.getProduct());
                    order.setQuantity(updatedOrder.getQuantity());
                    order.setPrice(updatedOrder.getPrice());
                    order.setStatus(updatedOrder.getStatus());
                    Order savedOrder = orderRepositories.save(order);
                    log.info("Updated order: " + savedOrder);
                    return ResponseEntity.ok(savedOrder);
                })
                .orElseThrow(() -> new GlobalExceptions("Order not found with id: " + id));
    }
    @DeleteMapping("/api/delete/all")
    public void DeleteAllOrders(){
        log.info("All orders deleted");
        orderRepositories.deleteAll();
    }
}


//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class MainController {
//
//    private final OrderRepositories orderRepositories;
//    private final ObjectMapper objectMapper;
//
//    @PostMapping("/api/new")
//    public void newOrder(@RequestBody Order order){
//        log.info("New order: " + orderRepositories.save(order));
//    }
//
//    @SneakyThrows
//    @GetMapping("/api/orders/all")
//    public String getOrders(){
//        List<Order> orders = orderRepositories.findAll();
//        log.info("All orders: " + objectMapper.writeValueAsString(orders));
//        return objectMapper.writeValueAsString(orders);
//    }
//
//    @GetMapping("/api/order")
//    public Order getOrderById(@RequestParam int id){
//        log.info("Order with id " + id + " : " + orderRepositories.findById(id));
//        return orderRepositories.findById(id).orElseThrow(() -> new GlobalExceptions("Order not found with id: " + id));
//    }
//
//    @DeleteMapping("/api/delete")
//    public String deleteOrder(@RequestParam int id){
//        if (!orderRepositories.existsById(id)){
//            throw new GlobalExceptions("Order not found with id: " + id);
//        }
//        orderRepositories.deleteById(id);
//        log.info("Order with id " + id + " deleted");
//        return "Order with id " + id + "deleted";
//    }
//
//    @PutMapping("/api/order/update")
//    public ResponseEntity<Order> updateOrder(@RequestParam int id, @RequestBody Order updatedOrder) {
//// return "No such order";
//// }
//// return orderRepositories.save(updatedOrder).toString();
//        return orderRepositories.findById(id)
//                .map(order -> {
//                    order.setProduct(updatedOrder.getProduct());
//                    order.setQuantity(updatedOrder.getQuantity());
//                    order.setPrice(updatedOrder.getPrice());
//                    order.setStatus(updatedOrder.getStatus());
//                    orderRepositories.save(order);
//                    log.info("Updated order: " + order);
//                    return ResponseEntity.ok(order);
//                })
//                .orElseThrow(() -> new GlobalExceptions("Order not found with id: " + id));
//    }
//
//    @DeleteMapping("/api/delete/all")
//    public void DeleteAllOrders(){
//        log.info("All orders deleted");
//        orderRepositories.deleteAll();
//    }
//}
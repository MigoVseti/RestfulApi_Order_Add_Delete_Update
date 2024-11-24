package com.migran.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migran.app.model.Order;
import com.migran.app.repositories.OrderRepositories;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Главный контроллер заказа", description = "Взаимодействие с заказами")
public class MainController {

    private final OrderRepositories orderRepositories;
    private final ObjectMapper objectMapper;

    @Operation(summary = "Создание заказа", description = "Позволяет создать новый заказ")
    @PostMapping("/api/new")
    public void newOrder(@RequestBody Order order){
        Order savedOrder = orderRepositories.save(order);
        log.info("New order: " + savedOrder);
    }

    @SneakyThrows
    @Operation(summary = "Получение списка заказов", description = "Позволяет получить список созданных заказов")
    @GetMapping("/api/orders/all")
    public String getOrders(){
        List<Order> orders = orderRepositories.findAll();
        String ordersJson = objectMapper.writeValueAsString(orders);
        log.info("All orders: " + ordersJson);
        return ordersJson;
    }

    @Operation(summary = "Получение заказа по уникальному номеру", description = "Позволяет получить созданый заказ по уникальному номеру")
    @GetMapping("/api/order")
    public Order getOrderById(@RequestParam int id){
        Optional<Order> orderOptional = orderRepositories.findById(id);
        Order order = orderOptional.orElseThrow(() -> new GlobalExceptions("Order not found with id: " + id));
        log.info("Order with id " + id + " : " + order);
        return order;
    }

    @Operation(summary = "Удаление заказа по уникальному номеру", description = "Позволяет удалить заказ по уникальному номеру")
    @DeleteMapping("/api/delete")
    public String deleteOrder(@RequestParam int id){
        if (!orderRepositories.existsById(id)){
            throw new GlobalExceptions("Order not found with id: " + id);
        }
        orderRepositories.deleteById(id);
        log.info("Order with id " + id + " deleted");
        return "Order with id " + id + " deleted";
    }

    @Operation(summary = "Изменение заказа", description = "Позволяет изменить созданый заказ")
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

    @Operation(summary = "Удаление всех заказов", description = "Позволяет удалить все создынные заказы")
    @DeleteMapping("/api/delete/all")
    public void DeleteAllOrders(){
        log.info("All orders deleted");
        orderRepositories.deleteAll();
    }
}
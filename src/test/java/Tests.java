import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import com.migran.app.controller.MainController;
import com.migran.app.enums.Status;
import com.migran.app.exceptions.GlobalExceptions;
import com.migran.app.model.Order;
import com.migran.app.repositories.OrderRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class Tests {

    @Mock
    private OrderRepositories orderRepositories;

    @InjectMocks
    private MainController mainController;

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order(1L, "Test Product", 10, BigDecimal.valueOf(100), Status.CREATED);
    }

    @Test
    public void testNewOrder() {
        when(orderRepositories.save(any(Order.class))).thenReturn(order);

        mainController.newOrder(order);

        verify(orderRepositories, times(1)).save(order);
    }

    @Test
    public void testGetOrderById() {
        when(orderRepositories.findById(1)).thenReturn(Optional.of(order));

        Order result = mainController.getOrderById(1);

        assertEquals(order, result);
        verify(orderRepositories, times(1)).findById(1);
    }

    @Test
    public void testGetOrderById_NotFound() {
        when(orderRepositories.findById(1)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptions.class, () -> mainController.getOrderById(1));
        verify(orderRepositories, times(1)).findById(1);
    }

    @Test
    public void testDeleteOrder() {
        when(orderRepositories.existsById(1)).thenReturn(true);

        String result = mainController.deleteOrder(1);

        assertEquals("Order with id 1 deleted", result);
        verify(orderRepositories, times(1)).existsById(1);
        verify(orderRepositories, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteOrder_NotFound() {
        when(orderRepositories.existsById(1)).thenReturn(false);

        assertThrows(GlobalExceptions.class, () -> mainController.deleteOrder(1));
        verify(orderRepositories, times(1)).existsById(1);
    }
    
    @Test
    public void testUpdateOrder() {
        Order updatedOrder = new Order(1L, "Updated Product", 20, BigDecimal.valueOf(200), Status.SHIPPED);
        when(orderRepositories.findById(1)).thenReturn(Optional.of(order));
        when(orderRepositories.save(any(Order.class))).thenReturn(updatedOrder);

        ResponseEntity<Order> result = mainController.updateOrder(1, updatedOrder);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedOrder, result.getBody());

        verify(orderRepositories, times(1)).findById(1);
        verify(orderRepositories, times(1)).save(order);
    }

    @Test
    public void testUpdateOrder_NotFound() {
        Order updatedOrder = new Order(1L, "Updated Product", 20, BigDecimal.valueOf(200), Status.DELIVERED);
        when(orderRepositories.findById(1)).thenReturn(Optional.empty());

        assertThrows(GlobalExceptions.class, () -> mainController.updateOrder(1, updatedOrder));
        verify(orderRepositories, times(1)).findById(1);
    }

    @Test
    public void testDeleteAllOrders() {
        mainController.DeleteAllOrders();

        verify(orderRepositories, times(1)).deleteAll();
    }
}
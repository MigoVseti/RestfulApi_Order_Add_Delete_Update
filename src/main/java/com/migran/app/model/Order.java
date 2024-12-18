package com.migran.app.model;

import com.migran.app.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный номер зкаказа", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotEmpty
    @Schema(description = "Название продукта", example = "Хлеб")
    private String product;

    @Positive
    @Min(value = 0, message = "Quantity < 0")
    @Schema(description = "Количество продукта", example = "10")
    private int quantity;

    @Positive
    @Min(value = 1, message = "Price < 1")
    @Schema(description = "Стоимость продукта", example = "50")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Статус заказа", allowableValues = {
            "CREATED",
            "SHIPPED",
            "DELIVERED"
    })
    private Status status;

    public Order(Long id, String product, int quantity, BigDecimal price, Status status) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity && Objects.equals(id, order.id) && Objects.equals(product, order.product) && Objects.equals(price, order.price) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, price, status);
    }
}

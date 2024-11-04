package com.migran.app.repositories;

import com.migran.app.model.Order;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositories extends JpaRepository<@Valid Order, Integer> {
}

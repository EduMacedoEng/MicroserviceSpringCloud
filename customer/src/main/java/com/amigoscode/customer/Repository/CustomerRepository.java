package com.amigoscode.customer.Repository;

import com.amigoscode.customer.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}

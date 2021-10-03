package com.github.iahrari.springrestmvc.repository;

import com.github.iahrari.springrestmvc.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}

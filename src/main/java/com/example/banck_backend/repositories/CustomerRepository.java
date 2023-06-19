package com.example.banck_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.banck_backend.entities.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

}

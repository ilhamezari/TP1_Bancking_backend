package com.example.banck_backend.repositories;

import com.example.banck_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String>{


}

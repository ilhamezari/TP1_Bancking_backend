package com.example.banck_backend.entities;

import com.example.banck_backend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4)
@Data @AllArgsConstructor @NoArgsConstructor

public class BankAccount {
    @Id
private String id;
private double balance;
private Date CreatedAT;
private AccountStatus status;
@ManyToOne
private Customer customer;
@OneToMany(mappedBy = "bankAccount")
private List<AOperation> aOperations;

}

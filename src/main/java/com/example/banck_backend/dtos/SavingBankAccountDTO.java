package com.example.banck_backend.dtos;
import com.example.banck_backend.enums.AccountStatus;
import lombok.Data;
import java.util.Date;


@Data

public class SavingBankAccountDTO extends BankAccountDTO{

private String id;
private double balance;
private Date CreatedAT;
private AccountStatus status;
private CustomerDTO customerDTO;
private double interestRate;


}

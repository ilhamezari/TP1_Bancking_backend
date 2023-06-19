package com.example.banck_backend.service;

import com.example.banck_backend.dtos.*;
import com.example.banck_backend.entities.BankAccount;
import com.example.banck_backend.entities.CurrentAccount;
import com.example.banck_backend.entities.Customer;
import com.example.banck_backend.entities.SavingAccount;
import com.example.banck_backend.exception.BalanceNotSufficientException;
import com.example.banck_backend.exception.BankAccountNotFoundException;
import com.example.banck_backend.exception.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

  CustomerDTO saveCostumer(CustomerDTO customerDTO);
  CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
  SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

  List<CustomerDTO> CUSTOMERLIST();
  BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

  List<BankAccountDTO> bankAccountList();

  CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

  CustomerDTO updateCustomer(CustomerDTO customerDTO);

  void deleteCustomer(Long customerId);

  List<AccountOperationDTO> AccountHistory(String accountId);
}

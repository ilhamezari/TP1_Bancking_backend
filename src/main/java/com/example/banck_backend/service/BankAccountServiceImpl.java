package com.example.banck_backend.service;

import com.example.banck_backend.dtos.*;
import com.example.banck_backend.entities.*;
import com.example.banck_backend.enums.OperationType;
import com.example.banck_backend.exception.BalanceNotSufficientException;
import com.example.banck_backend.mappers.BankAccountMapperImpl;
import com.example.banck_backend.repositories.BankAccountRepository;
import com.example.banck_backend.repositories.BankOpAccountRepository;
import com.example.banck_backend.repositories.CustomerRepository;
import com.example.banck_backend.exception.CustomerNotFoundException;
import com.example.banck_backend.exception.BankAccountNotFoundException;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j


public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private BankOpAccountRepository bankOpAccountRepository;

    private BankAccountMapperImpl dtoMapper;

    //public BankAccountServiceImpl() {super();}

    //Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public CustomerDTO saveCostumer(CustomerDTO customerDTO) {
        log.info("saving a new customer!");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
     Customer savedCustomer= customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if(customer==null)
            throw new CustomerNotFoundException("Customer not found!");

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAT(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount  = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if(customer==null)
            throw new CustomerNotFoundException("Customer not found!");

        SavingAccount savingAccount  = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAT(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount  = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }


    @Override
    public List<CustomerDTO> CUSTOMERLIST() {
       List<Customer> customers = customerRepository.findAll();
       List<CustomerDTO> customerDTOS=customers.stream()
               .map(customer -> dtoMapper.fromCustomer(customer))
               .collect(Collectors.toList());

       //customers.stream().map(cust-> dtoMapper.fromCustomer(cust))
       /*
       List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer:customers){
            CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }*/
        return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundException("BankAccount not found!"));
if (bankAccount instanceof SavingAccount){
    SavingAccount savingAccount =(SavingAccount) bankAccount;
    return dtoMapper.fromSavingBankAccount(savingAccount);
}else {
        CurrentAccount currentAccount =(CurrentAccount) bankAccount;
    return dtoMapper.fromCurrentBankAccount(currentAccount);
}
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundException("BankAccount not found!"));
       if(bankAccount.getBalance()<amount)
           throw new  BalanceNotSufficientException("Balance not sufficient");

        AOperation aOperation = new AOperation();
        aOperation.setType(OperationType.DEBIT);
        aOperation.setAmount(amount);
        aOperation.setDescription(description);
        aOperation.setOperationDate(new Date());
        aOperation.setBankAccount(bankAccount);
        bankOpAccountRepository.save(aOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundException("BankAccount not found!"));

        AOperation aOperation = new AOperation();

        aOperation.setType(OperationType.CREDIT);
        aOperation.setAmount(amount);
        aOperation.setDescription(description);
        aOperation.setOperationDate(new Date());
        aOperation.setBankAccount(bankAccount);
        bankOpAccountRepository.save(aOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }


    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
       debit(accountIdSource,amount,"transfer to "+accountIdDestination);
       credit(accountIdDestination,amount,"transfer from "+accountIdSource);

    }
    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
            List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
                if (bankAccount instanceof SavingAccount) {
                    SavingAccount savingAccount = (SavingAccount) bankAccount;
                    return dtoMapper.fromSavingBankAccount(savingAccount);
                } else {
                    CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                    return dtoMapper.fromCurrentBankAccount(currentAccount);
                }
            }).collect(Collectors.toList());
            return bankAccountDTOS;
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
      Customer customer=  customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer Not found"));
      return dtoMapper.fromCustomer(customer);
    }
@Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("saving a new customer!");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
@Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }
@Override
public List<AccountOperationDTO> AccountHistory(String accountId){
        List<AOperation> accountOperations=bankOpAccountRepository.findByBankAccountId(accountId);
       return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
}
}

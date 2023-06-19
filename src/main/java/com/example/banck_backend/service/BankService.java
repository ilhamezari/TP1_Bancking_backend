package com.example.banck_backend.service;

import com.example.banck_backend.entities.BankAccount;
import com.example.banck_backend.entities.CurrentAccount;
import com.example.banck_backend.entities.SavingAccount;
import com.example.banck_backend.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void consulter() {
        BankAccount bankAccount =
                bankAccountRepository.findById("2bee821d-de95-4536-b1bb-9626d2ded54d").orElse(null);
        if (bankAccount != null) {
            System.out.println("**************************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatedAT());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());

            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Over draft" + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate" + ((SavingAccount) bankAccount).getInterestRate());

            }
            bankAccount.getAOperations().forEach(op -> {
                /** System.out.println("******************************");
                 System.out.println(op.getType());
                 System.out.println(op.getOperationDate());
                 System.out.println(op.getAmount());*/
                System.out.println(op.getType() + "\t" + op.getOperationDate() + "\t" + op.getAmount());
            });

        }

    }}

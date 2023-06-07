package com.example.banck_backend.repositories;

import com.example.banck_backend.entities.AOperation;
import com.example.banck_backend.entities.BankAccount;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface BanckOpAccountRepository extends JpaRepository<AOperation,Long>{


    CommandLineRunner start(CustomerRepository customerRepository,
                            BanckAccountRepository banckAccountRepository,
                            BanckOpAccountRepository banckOperationAccountRepository){
        return args -> (
                Stream.of("omar","Ali","Amal").forEach (case->{

        }



        }
                                                     

        }
                )
    }
}

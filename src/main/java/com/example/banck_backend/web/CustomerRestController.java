package com.example.banck_backend.web;

import com.example.banck_backend.dtos.CustomerDTO;
import com.example.banck_backend.entities.Customer;
import com.example.banck_backend.exception.CustomerNotFoundException;
import com.example.banck_backend.service.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
@GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.CUSTOMERLIST();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
         return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public  CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
    return bankAccountService.saveCostumer(customerDTO);
}
@PutMapping("/customers/{customerId}")
public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
customerDTO.setId(customerId);
return bankAccountService.updateCustomer(customerDTO);

}
@DeleteMapping("/customers/{id}")
public void deleteCustomer(@PathVariable Long id){
    bankAccountService.deleteCustomer(id);

}
}

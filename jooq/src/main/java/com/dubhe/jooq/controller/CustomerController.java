package com.dubhe.jooq.controller;

import com.dubhe.jooq.model.tables.pojos.Customer;
import com.dubhe.jooq.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getAll() {
        return customerService.getCustomers();
    }

    @PostMapping("/customers")
    public void add(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }
}

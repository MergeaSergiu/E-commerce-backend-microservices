package com.mycompany.customer;

import java.util.List;

public interface CustomerService {

    void registerCustomer(CustomerRequest customerRequest);

    List<Customer> getCustomers();
}

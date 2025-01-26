package com.mycompany.customer;

import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudCheckClient fraudCheckClient;
    public CustomerServiceImpl(CustomerRepository customerRepository,FraudCheckClient fraudCheckClient) {
        this.customerRepository = customerRepository;
        this.fraudCheckClient = fraudCheckClient;
    }

    @Override
    public void registerCustomer(CustomerRequest customerRequest) {
        Customer foundCustomer = customerRepository.findByEmail(customerRequest.email());
        if(foundCustomer != null) { throw new EntityExistsException("Customer already exists"); }
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .password(customerRequest.password())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudCheckClient.checkFraud(customer.getId());

        assert fraudCheckResponse != null;
        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}

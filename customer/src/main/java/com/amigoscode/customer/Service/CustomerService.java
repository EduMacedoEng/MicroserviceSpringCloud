package com.amigoscode.customer.Service;

import com.amigoscode.customer.CustomerRegistrationRequest;
import com.amigoscode.customer.Entity.Customer;
import com.amigoscode.customer.Repository.CustomerRepository;
import com.amigoscode.fraud.FraudCheckResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer);
        // todo: check if fraudster
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
                );
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        // todo: send notification

    }
}

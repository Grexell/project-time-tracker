package by.dima.project.service;

import by.dima.model.User;
import by.dima.project.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<Customer> getCustomers(User manager);

    Mono<Customer> createCustomer(Customer customer);
    Mono<Customer> updateCustomer(Customer customer);
    Mono<Customer> deleteCustomer(Long customerId);
}

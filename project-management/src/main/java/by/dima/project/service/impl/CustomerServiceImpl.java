package by.dima.project.service.impl;

import by.dima.model.User;
import by.dima.project.dao.CustomerDao;
import by.dima.project.model.Customer;
import by.dima.project.service.CustomerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Flux<Customer> getCustomers(User manager) {
        return customerDao.findAllByManagerId(manager.getId());
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        customer.setId(null);
        return customerDao.save(customer);
    }

    @Override
    public Mono<Customer> updateCustomer(Customer customer) {
        return customerDao.findById(customer.getId())
                .doOnNext(saved -> {
                    saved.setName(customer.getName());
                    saved.setContact(customer.getContact());
                })
                .flatMap(customerDao::save);
    }

    @Override
    public Mono<Customer> deleteCustomer(Long customerId) {
        return customerDao.findById(customerId)
                .flatMap(customer -> customerDao.delete(customer).thenReturn(customer));
    }
}

package by.dima.project.controller;

import by.dima.model.User;
import by.dima.project.model.Customer;
import by.dima.project.service.CustomerService;
import by.dima.utils.TokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static by.dima.utils.TokenUtils.MANAGER_ROLE;
import static by.dima.utils.TokenUtils.is;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Flux<Customer>> getCustomers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(customerService.getCustomers(user));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Mono<Customer>> createCustomer(@RequestBody Customer customer, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(customerService.createCustomer(customer));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<Customer>> updateCustomer(@PathVariable("id") Long customerId, @RequestBody Customer customer, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            customer.setId(customerId);
            return ResponseEntity.ok(customerService.updateCustomer(customer));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Mono<Customer>> deleteCustomer(@PathVariable("id") Long customerId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(customerService.deleteCustomer(customerId));
        }
        return ResponseEntity.badRequest().build();
    }
}

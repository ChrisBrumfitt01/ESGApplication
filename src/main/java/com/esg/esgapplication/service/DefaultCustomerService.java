package com.esg.esgapplication.service;

import com.esg.esgapplication.exception.NoSuchCustomerException;
import com.esg.esgapplication.model.Customer;
import com.esg.esgapplication.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCustomerService implements CustomerService {

  private final CustomerRepository customerRepository;

  @Override
  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public Customer getCustomer(String customerRef) {
    return customerRepository.findById(customerRef).orElseThrow(
        () -> new NoSuchCustomerException(String.format("No customer exists with the ref: %s", customerRef))
    );
  }
}

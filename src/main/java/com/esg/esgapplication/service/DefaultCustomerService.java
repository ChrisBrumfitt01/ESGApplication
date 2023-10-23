package com.esg.esgapplication.service;

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
}

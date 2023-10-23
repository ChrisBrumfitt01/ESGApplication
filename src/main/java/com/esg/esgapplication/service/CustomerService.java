package com.esg.esgapplication.service;

import com.esg.esgapplication.model.Customer;

public interface CustomerService {

  Customer createCustomer(Customer customer);

  Customer getCustomer(String customerRef);

}

package com.esg.esgapplication.repository;

import com.esg.esgapplication.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {

}

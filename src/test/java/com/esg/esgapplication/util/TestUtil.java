package com.esg.esgapplication.util;

import com.esg.esgapplication.csv.model.CustomerCsvBean;
import com.esg.esgapplication.model.Customer;
import java.util.UUID;

public class TestUtil {

  public static Customer createCustomer() {
    Customer customer = new Customer();
    customer.setCustomerRef(UUID.randomUUID().toString());
    customer.setCustomerName("Chris");
    customer.setAddressLine1("Flat 1");
    customer.setAddressLine2("2 Main Street");
    customer.setTown("Manchester");
    customer.setCounty("Greater Manchester");
    customer.setCounty("UK");
    customer.setCounty("M1 1AA");
    return customer;
  }

  public static CustomerCsvBean createCustomerCsvBean() {
    CustomerCsvBean customer = new CustomerCsvBean();
    customer.setCustomerRef(UUID.randomUUID().toString());
    customer.setCustomerName("Chris");
    customer.setAddressLine1("Flat 1");
    customer.setAddressLine2("2 Main Street");
    customer.setTown("Manchester");
    customer.setCounty("Greater Manchester");
    customer.setCounty("UK");
    customer.setCounty("M1 1AA");
    return customer;
  }

}

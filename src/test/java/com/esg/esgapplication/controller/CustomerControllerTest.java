package com.esg.esgapplication.controller;

import static com.esg.esgapplication.util.TestUtil.createCustomer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import com.esg.esgapplication.model.Customer;
import com.esg.esgapplication.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired private ObjectMapper mapper;

  @Autowired private CustomerRepository customerRepository;

  @Test
  @DisplayName("When a POST is sent to /customer then a customer is persisted")
  void customerCreatedCorrectly() throws Exception {
    Customer customer = createCustomer();

    mockMvc.perform(
        post("/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(customer)))
        .andExpect(status().isCreated());

    Optional<Customer> savedCustomer = customerRepository.findById(customer.getCustomerRef());
    assertThat(savedCustomer).isNotEmpty().hasValue(customer);
  }

  @Test
  @DisplayName("When a GET is sent to /customer/customerRef then a customer is retrieved")
  void customerRetrievedCorrectly() throws Exception {
    Customer customer = createCustomer();
    customerRepository.save(customer);

    mockMvc.perform(get(String.format("/customer/%s", customer.getCustomerRef())))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(customer)));
  }

  @Test
  @DisplayName("When a GET is sent to /customer/customerRef with an invalid ID then a 404 is received")
  void customerRetrievedInvalidID() throws Exception {
    mockMvc.perform(get("/customer/invalid_id"))
        .andExpect(status().isNotFound());
  }

}

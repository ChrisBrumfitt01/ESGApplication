package com.esg.esgapplication.service;

import static com.esg.esgapplication.util.TestUtil.createCustomer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import com.esg.esgapplication.model.Customer;
import com.esg.esgapplication.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DefaultCustomerServiceTest {

  @Mock private CustomerRepository customerRepository;

  @InjectMocks
  private DefaultCustomerService customerService;

  @Test
  public void create_shouldCallRepositoryAndReturnResult() {
    Customer toSave = createCustomer();
    Customer savedCustomer = createCustomer();
    when(customerRepository.save(any())).thenReturn(savedCustomer);

    Customer actual = customerService.createCustomer(toSave);
    verify(customerRepository).save(toSave);
    assertThat(actual).isSameAs(savedCustomer);
  }

}

package com.esg.esgapplication.service;

import static com.esg.esgapplication.util.TestUtil.createCustomer;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import com.esg.esgapplication.exception.NoSuchCustomerException;
import com.esg.esgapplication.model.Customer;
import com.esg.esgapplication.repository.CustomerRepository;
import java.util.Optional;
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

  @Test
  public void getCustomer_shouldCallRepositoryWithCorrectId_andGetAndReturnACustomer() {
    String id = "123";
    Customer customer = createCustomer();
    when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

    Customer actual = customerService.getCustomer(id);
    verify(customerRepository).findById(id);
    assertThat(actual).isSameAs(customer);
  }

  @Test
  public void getCustomer_shouldThrowException_whenRepositoryFindsNoCustomer() {
    String id = "123";
    when(customerRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(NoSuchCustomerException.class, () -> {
      customerService.getCustomer(id);
    });
  }

}

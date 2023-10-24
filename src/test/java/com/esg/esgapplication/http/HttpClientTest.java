package com.esg.esgapplication.http;

import static com.esg.esgapplication.util.TestUtil.createCustomerCsvBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.esg.esgapplication.csv.model.CustomerCsvBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class HttpClientTest {

  @Mock
  private RestTemplate restTemplate;

  @Captor
  ArgumentCaptor<HttpEntity<CustomerCsvBean>> captor;

  @InjectMocks
  private HttpClient httpClient;

  @Test
  public void createCustomer_sendsPostRequestToCorrectUrlWithCorrectBody() {
    CustomerCsvBean customer = createCustomerCsvBean();
    ResponseEntity response = ResponseEntity.status(HttpStatus.CREATED).build();

    when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(response);
    httpClient.createCustomer(customer);

    verify(restTemplate).postForEntity(eq("http://localhost:8080/customer"), captor.capture(), eq(String.class));
    assertThat(captor.getValue().getBody()).isEqualTo(customer);
  }
  @Test
  public void createCustomer_returnsTrue_whenResponseStatusIs2xx() {
    CustomerCsvBean customer = createCustomerCsvBean();
    ResponseEntity response = ResponseEntity.status(HttpStatus.CREATED).build();

    when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(response);
    boolean result = httpClient.createCustomer(customer);

    assertThat(result).isTrue();
  }

  @Test
  public void createCustomer_returnsFalse_whenResponseStatusIsNot2xx() {
    CustomerCsvBean customer = createCustomerCsvBean();
    ResponseEntity response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(response);
    boolean result = httpClient.createCustomer(customer);

    assertThat(result).isFalse();
  }

}

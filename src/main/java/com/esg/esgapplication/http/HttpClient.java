package com.esg.esgapplication.http;

import com.esg.esgapplication.csv.model.CustomerCsvBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@RequiredArgsConstructor
@Component
public class HttpClient {

  private static final String URL = "localhost:8080/customer";

  private final RestTemplate restTemplate;

  public boolean createCustomer(CustomerCsvBean customer) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<CustomerCsvBean> requestEntity = new HttpEntity<>(customer, headers);
    ResponseEntity response = restTemplate.postForEntity(URL, requestEntity, String.class);
    return response.getStatusCode().is2xxSuccessful();
  }

}

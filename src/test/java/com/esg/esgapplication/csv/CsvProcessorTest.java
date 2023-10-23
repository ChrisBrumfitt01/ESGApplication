package com.esg.esgapplication.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.esg.esgapplication.csv.model.CustomerCsvBean;
import com.esg.esgapplication.exception.CsvProcessingException;
import com.esg.esgapplication.http.HttpClient;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CsvProcessorTest {

  private static final String CSV_FILEPATH = "src/test/resources/customers.csv";
  private static final String CSV_INVALID_LINES = "src/test/resources/customers_with_invalid_lines.csv";

  @Mock private HttpClient httpClient;

  @Captor
  private ArgumentCaptor<CustomerCsvBean> captor;

  @InjectMocks
  private CsvProcessor csvProcessor;

  @Test
  public void process_withValidCsv_shouldReturnNumberOfLinesProcessed_andSendEachToHttpClient() {
    when(httpClient.createCustomer(any())).thenReturn(true);
    int linesProcessed = csvProcessor.process(CSV_FILEPATH);
    assertEquals(3, linesProcessed);

    verify(httpClient, times(3)).createCustomer(captor.capture());
    List<CustomerCsvBean> actualValues = captor.getAllValues();

    assertThat(actualValues.stream().map(CustomerCsvBean::getCustomerRef)).contains("123", "456", "789");
  }

  @Test
  public void process_withInvalidFilePath_shouldThrowException() {
    assertThrows(CsvProcessingException.class, () -> {
      csvProcessor.process("this file doesn't exist.csv");
    });
  }

  @Test
  public void process_withInvalidCSVLine_shouldProcessValidLines() {
    when(httpClient.createCustomer(any())).thenReturn(true);
    int linesProcessed = csvProcessor.process(CSV_INVALID_LINES);
    assertEquals(2, linesProcessed);

    verify(httpClient, times(2)).createCustomer(captor.capture());
    List<CustomerCsvBean> actualValues = captor.getAllValues();

    assertThat(actualValues.stream().map(CustomerCsvBean::getCustomerRef)).contains("123", "789");
  }

  @Test
  public void process_withValidCsv_shouldReturn1_whenOtherLinesFailToProcess() {
    when(httpClient.createCustomer(any())).thenReturn(true).thenReturn(false);
    int linesProcessed = csvProcessor.process(CSV_FILEPATH);
    assertEquals(1, linesProcessed);

    verify(httpClient, times(3)).createCustomer(captor.capture());
    List<CustomerCsvBean> actualValues = captor.getAllValues();

    assertThat(actualValues.stream().map(CustomerCsvBean::getCustomerRef)).contains("123", "456", "789");
  }

}

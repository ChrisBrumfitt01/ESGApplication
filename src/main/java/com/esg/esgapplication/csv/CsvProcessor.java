package com.esg.esgapplication.csv;

import com.esg.esgapplication.config.CsvProcessorConfig;
import com.esg.esgapplication.csv.model.CustomerCsvBean;
import com.esg.esgapplication.exception.CsvProcessingException;
import com.esg.esgapplication.http.HttpClient;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CsvProcessor {
  private final HttpClient httpClient;
  private final CsvProcessorConfig config;

  public int process(String csvFilePath) {
    ExecutorService executorService = Executors.newFixedThreadPool(config.getThreadCount());
    AtomicInteger linesProcessed = new AtomicInteger(0);

    try {
      Reader reader = new BufferedReader(new FileReader(csvFilePath));
      CSVReader csvReader = new CSVReaderBuilder(reader)
          .withSkipLines(1)
          .build();

      String[] line;
      while ((line = csvReader.readNext()) != null) {
        final String[] finalLine = line;
        executorService.execute(() -> {
          try {
            CustomerCsvBean customer = mapToCustomer(finalLine);
            if (httpClient.createCustomer(customer)) {
              linesProcessed.incrementAndGet();
            }
          } catch (Exception e) {
            log.error("An error occurred while processing a line [{}]: {}", Arrays.toString(finalLine), e);
          }
        });
      }

      executorService.shutdown();
      if (!executorService.awaitTermination(config.getTimeout(), TimeUnit.MILLISECONDS)) {
        throw new CsvProcessingException("Process timed out. CSV file not completed");
      }

    } catch (FileNotFoundException e) {
      throw new CsvProcessingException("File path does not exist: " + csvFilePath);
    } catch (IOException e) {
      throw new CsvProcessingException("An error occurred when trying to read the CSV file");
    } catch (CsvValidationException e) {
      throw new CsvProcessingException("An error occurred with validating the CSV file");
    } catch (InterruptedException e) {
      throw new CsvProcessingException("Thread was interrupted");
    }

    return linesProcessed.intValue();
  }

  private CustomerCsvBean mapToCustomer(String[] data) {
    CustomerCsvBean customer = new CustomerCsvBean();
    customer.setCustomerRef(data[0]);
    customer.setCustomerName(data[1]);
    customer.setAddressLine1(data[2]);
    customer.setAddressLine2(data[3]);
    customer.setTown(data[4]);
    customer.setCounty(data[5]);
    customer.setCountry(data[6]);
    customer.setPostcode(data[7]);
    return customer;
  }
}

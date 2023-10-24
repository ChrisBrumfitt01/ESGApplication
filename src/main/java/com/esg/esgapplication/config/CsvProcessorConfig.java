package com.esg.esgapplication.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CsvProcessorConfig {
  @Value("${csv-processor.timeout}")
  private int timeout;

  @Value("${csv-processor.thread-count}")
  private int threadCount;

}

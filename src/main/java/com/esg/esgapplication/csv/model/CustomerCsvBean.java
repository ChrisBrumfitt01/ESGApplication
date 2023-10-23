package com.esg.esgapplication.csv.model;

import lombok.Data;

@Data
public class CustomerCsvBean {
  private String customerRef;
  private String customerName;
  private String addressLine1;
  private String addressLine2;
  private String town;
  private String county;
  private String country;
  private String postcode;
}

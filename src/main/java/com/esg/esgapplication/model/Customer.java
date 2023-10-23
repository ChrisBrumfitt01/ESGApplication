package com.esg.esgapplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Customer {

  @Id
  private String customerRef;
  private String customerName;
  private String addressLine1;
  private String addressLine2;
  private String town;
  private String county;
  private String country;
  private String postcode;


}

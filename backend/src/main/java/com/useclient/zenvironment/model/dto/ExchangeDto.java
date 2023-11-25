package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExchangeDto {

  private String  exchangeId;
  private String vendorId;
  private String receiverId;
  private String description;
  private String profileImageUrl;
  private String productImageUrl;
  private String gardenName;
  private boolean accepted;
}

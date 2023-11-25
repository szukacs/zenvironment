package com.useclient.zenvironment.service;

import com.useclient.zenvironment.model.dao.Exchange;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dto.ExchangeDto;
import com.useclient.zenvironment.repository.ExchangeRepository;
import com.useclient.zenvironment.repository.GardenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeService {

  @Autowired
  private ExchangeRepository exchangeRepository;
  @Autowired
  private GardenRepository gardenRepository;

  public void createExchange(ExchangeDto exchangeDto){
    Garden garden = gardenRepository.findById(UUID.fromString(exchangeDto.getVendorId())).get();

    Exchange exchange = Exchange.builder()
        .garden(garden)
        .description(exchangeDto.getDescription())
        .build();

    exchangeRepository.save(exchange);
  }

  public List<Exchange> getAllExchangesBelongingToCommunity(String communityId){
    //List<Garden> gardens = gardenRepository.findAllByCommunityId(communityId);

    return new ArrayList<>();
  }
}

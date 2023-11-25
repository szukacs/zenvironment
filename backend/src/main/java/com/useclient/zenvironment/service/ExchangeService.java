package com.useclient.zenvironment.service;

import com.useclient.zenvironment.mapper.MainMapper;
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

  private static final String EXCEPTION_MESSAGE_NOT_FOUND = "Couldn't find garden wih given id";
  @Autowired
  private MainMapper mapper;

  public List<ExchangeDto> createExchange(ExchangeDto exchangeDto) {
    Garden garden = gardenRepository.findById(UUID.fromString(exchangeDto.getVendorId()))
        .orElseThrow(() -> new IllegalArgumentException(EXCEPTION_MESSAGE_NOT_FOUND + exchangeDto.getVendorId()));

    Exchange exchange = Exchange.builder()
        .garden(garden)
        .description(exchangeDto.getDescription())
        .build();

    exchangeRepository.save(exchange);
    return findAllExchangesBelongingToGarden(exchangeDto.getVendorId());
  }

  public List<ExchangeDto> findAllExchangesBelongingToCommunity(String communityId) {
    List<Garden> gardens = gardenRepository.findAllGardensByCommunityId(UUID.fromString(communityId));

    List<Exchange> exchanges = new ArrayList<>();
    gardens.forEach(garden -> exchanges.addAll(garden.getExchanges()));

    List<ExchangeDto> exchangeDtos = new ArrayList<>();
    exchanges
        .stream()
        .filter(exchange -> !exchange.isAccepted())
        .forEach(exchange -> exchangeDtos.add(mapper.exchangeDto(exchange)));
    return exchangeDtos;
  }

  public void updateStatusOfExchange(ExchangeDto exchangeDto) {
    exchangeRepository.updateById(exchangeDto.getVendorId(), exchangeDto.getReceiverId());
  }

  public List<ExchangeDto> findAllExchangesBelongingToGarden(String gardenId){
   List<Exchange> exchanges = exchangeRepository.findAllByIdNotAccepted(UUID.fromString(gardenId), gardenId);
   List<ExchangeDto> exchangeDtos = new ArrayList<>();
   exchanges.forEach(exchange -> exchangeDtos.add(mapper.exchangeDto(exchange)));
   return exchangeDtos;
  }
}

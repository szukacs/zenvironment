package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Exchange;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, String> {
  @Modifying
  @Transactional
  @Query("UPDATE Exchange  exchange set exchange.accepted = true, exchange.receiverId = :receiverId " +
      "WHERE exchange.id = :exchangeId")
  void updateById(@Param("exchangeId") UUID exchangeId, @Param("receiverId") UUID receiverId);

  @Query("SELECT exchange from Exchange  exchange where exchange.garden.id = :gardenId or exchange.receiverId = :gardenIdString " +
      "order by exchange.accepted desc")
  List<Exchange> findAllByIdNotAccepted(@Param("gardenId") UUID gardenId, @Param("gardenIdString") String gardenIdString);
}

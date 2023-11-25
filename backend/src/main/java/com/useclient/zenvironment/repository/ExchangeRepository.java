package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Exchange;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, String> {
  @Modifying
  @Transactional
  @Query("UPDATE Exchange  exchange set exchange.accepted = true, exchange.receiverId = :receiverId " +
      "WHERE exchange.garden.id = :vendorId")
  void updateById(@Param("vendorId") String vendorId, @Param("receiverId") String receiverId);

  @Query("SELECT  exchange from Exchange  exchange where exchange.garden.id = :vendorId and exchange.accepted = false")
  List<Exchange> findAllByIdNotAccepted(@Param("vendorId") String vendorId);
}

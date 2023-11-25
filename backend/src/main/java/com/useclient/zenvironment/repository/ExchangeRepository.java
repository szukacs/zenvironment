package com.useclient.zenvironment.repository;

import com.useclient.zenvironment.model.dao.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, String> {
}

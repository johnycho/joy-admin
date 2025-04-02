package com.nhn.pebblewebback.infra.vo;

import com.nhn.pebblewebback.infra.constant.DbConnectionType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? DbConnectionType.REPLICA : DbConnectionType.MASTER;
  }
}


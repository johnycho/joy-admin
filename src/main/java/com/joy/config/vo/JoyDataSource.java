package com.joy.config.vo;

import com.joy.config.constant.DbConnectionType;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JoyDataSource {

  private DataSource master;
  private DataSource replica;

  public JoyDataSource master(DataSource master) {
    this.master = master;
    return this;
  }

  public JoyDataSource replica(DataSource replica) {
    this.replica = replica;
    return this;
  }

  public DynamicRoutingDataSource createRoutingDataSource() {
    final DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();

    final Map<Object, Object> dataSourceMap = new HashMap<>();

    dataSourceMap.put(DbConnectionType.MASTER, this.master);
    dataSourceMap.put(DbConnectionType.REPLICA, this.replica);

    routingDataSource.setTargetDataSources(dataSourceMap);
    routingDataSource.setDefaultTargetDataSource(this.master);
    routingDataSource.afterPropertiesSet();

    return routingDataSource;
  }
}

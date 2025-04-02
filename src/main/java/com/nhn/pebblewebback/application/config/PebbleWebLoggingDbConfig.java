package com.nhn.pebblewebback.application.config;

import com.nhn.pebblewebback.infra.util.DataSourceBindUtils;
import com.nhn.pebblewebback.infra.util.DataSourceLoggingUtils;
import com.nhn.pebblewebback.infra.util.EntityManagerUtils;
import com.nhn.pebblewebback.infra.vo.PebbleWebDataSource;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = {
    "com.nhn.pebblewebback.infra.repository.jpa.logging",
}, // service jpa Repository 경로
    entityManagerFactoryRef = "pebbleWebLoggingEntityManager", transactionManagerRef = "pebbleWebLoggingTransactionManager")
public class PebbleWebLoggingDbConfig {

  private static final String ENTITY_PACKAGE = "com.nhn.pebblewebback.domain.entity.logging";

  private final Environment env;

  @Bean(initMethod = "migrateFlyway")
  public FlywayConfig pebbleWebLoggingFlywayConfig() {
    return new FlywayConfig(pebbleWebLoggingDataSource(), "flyway/logging");
  }

  @Bean
  public DataSource pebbleWebLoggingDataSource() {
    return new LazyConnectionDataSourceProxy(
        DataSourceLoggingUtils.apply(new PebbleWebDataSource()
            .master(DataSourceBindUtils.bind(env, "spring.datasource.hikari.logging-master"))
            .replica(DataSourceBindUtils.bind(env, "spring.datasource.hikari.logging-replica"))
            .createRoutingDataSource())
    );
  }

  @DependsOn({"pebbleWebLoggingDataSource", "pebbleWebLoggingFlywayConfig"})
  @Bean(name = "pebbleWebLoggingEntityManager")
  public LocalContainerEntityManagerFactoryBean pebbleWebLoggingEntityManager(final JpaProperties properties, final DataSource pebbleWebLoggingDataSource) {
    return EntityManagerUtils.createEntityManager(properties, pebbleWebLoggingDataSource, ENTITY_PACKAGE);
  }

  @Bean(name = "pebbleWebLoggingTransactionManager")
  public PlatformTransactionManager pebbleWebLoggingTransactionManager(final EntityManagerFactory pebbleWebLoggingEntityManager) {
    return new JpaTransactionManager(pebbleWebLoggingEntityManager);
  }
}

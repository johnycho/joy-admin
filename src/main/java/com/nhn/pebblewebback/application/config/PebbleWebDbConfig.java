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
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = {
    "com.nhn.pebblewebback.infra.repository.jpa.web",
}, // service jpa Repository 경로
    entityManagerFactoryRef = "pebbleWebEntityManager", transactionManagerRef = "pebbleWebTransactionManager")
public class PebbleWebDbConfig {

  private static final String ENTITY_PACKAGE = "com.nhn.pebblewebback.domain.entity.web";

  private final Environment env;

  @Bean(initMethod = "migrateFlyway", name = "pebbleWebFlywayConfig")
  public FlywayConfig pebbleWebFlywayConfig() {
    return new FlywayConfig(pebbleWebDataSource(), "flyway/web");
  }

  @Bean
  public DataSource pebbleWebDataSource() {
    return new LazyConnectionDataSourceProxy(
        DataSourceLoggingUtils.apply(new PebbleWebDataSource()
            .master(DataSourceBindUtils.bind(env, "spring.datasource.hikari.pebble-web-master"))
            .replica(DataSourceBindUtils.bind(env, "spring.datasource.hikari.pebble-web-replica"))
            .createRoutingDataSource())
    );
  }

  @DependsOn({"pebbleWebDataSource", "pebbleWebFlywayConfig"})
  @Bean(name = "pebbleWebEntityManager")
  public LocalContainerEntityManagerFactoryBean pebbleWebEntityManager(final JpaProperties properties, final DataSource pebbleWebDataSource) {
    return EntityManagerUtils.createEntityManager(properties, pebbleWebDataSource, ENTITY_PACKAGE);
  }

  @Primary
  @Bean(name = "pebbleWebTransactionManager")
  public PlatformTransactionManager pebbleWebTransactionManager(final EntityManagerFactory pebbleWebEntityManager) {
    return new JpaTransactionManager(pebbleWebEntityManager);
  }
}

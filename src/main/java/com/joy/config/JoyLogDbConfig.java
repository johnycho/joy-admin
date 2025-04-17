package com.joy.config;

import com.joy.config.util.DataSourceBindUtils;
import com.joy.config.util.DataSourceLoggingUtils;
import com.joy.config.util.EntityManagerUtils;
import com.joy.config.vo.JoyDataSource;
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
    "com.joy.log",
}, // service jpa Repository 경로
    entityManagerFactoryRef = "joyLogEntityManager", transactionManagerRef = "joyLogTransactionManager")
public class JoyLogDbConfig {

  private static final String ENTITY_PACKAGE = "com.joy.log";

  private final Environment env;

  @Bean(initMethod = "migrateFlyway")
  public FlywayConfig joyLogFlywayConfig() {
    return new FlywayConfig(joyLogDataSource(), "flyway/logging");
  }

  @Bean
  public DataSource joyLogDataSource() {
    return new LazyConnectionDataSourceProxy(
        DataSourceLoggingUtils.apply(new JoyDataSource()
                                         .master(DataSourceBindUtils.bind(env, "spring.datasource.hikari.logging-master"))
                                         .replica(DataSourceBindUtils.bind(env, "spring.datasource.hikari.logging-replica"))
                                         .createRoutingDataSource())
    );
  }

  @DependsOn({ "joyLogDataSource", "joyLogFlywayConfig" })
  @Bean(name = "joyLogEntityManager")
  public LocalContainerEntityManagerFactoryBean joyLogEntityManager(final JpaProperties properties, final DataSource joyLogDataSource) {
    return EntityManagerUtils.createEntityManager(properties, joyLogDataSource, ENTITY_PACKAGE);
  }

  @Bean(name = "joyLogTransactionManager")
  public PlatformTransactionManager joyLogTransactionManager(final EntityManagerFactory joyLogEntityManager) {
    return new JpaTransactionManager(joyLogEntityManager);
  }
}

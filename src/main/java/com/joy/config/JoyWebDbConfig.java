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
    "com.joy.web",
}, // service jpa Repository 경로
    entityManagerFactoryRef = "joyWebEntityManager", transactionManagerRef = "joyWebTransactionManager")
public class JoyWebDbConfig {

  private static final String ENTITY_PACKAGE = "com.joy.web";

  private final Environment env;

  @Bean(initMethod = "migrateFlyway", name = "joyWebFlywayConfig")
  public FlywayConfig joyWebFlywayConfig() {
    return new FlywayConfig(joyWebDataSource(), "flyway/web");
  }

  @Bean
  public DataSource joyWebDataSource() {
    return new LazyConnectionDataSourceProxy(
        DataSourceLoggingUtils.apply(new JoyDataSource()
                                         .master(DataSourceBindUtils.bind(env, "spring.datasource.hikari.joy-admin-master"))
                                         .replica(DataSourceBindUtils.bind(env, "spring.datasource.hikari.joy-admin-replica"))
                                         .createRoutingDataSource())
    );
  }

  @DependsOn({ "joyWebDataSource", "joyWebFlywayConfig" })
  @Bean(name = "joyWebEntityManager")
  public LocalContainerEntityManagerFactoryBean joyWebEntityManager(final JpaProperties properties, final DataSource joyWebDataSource) {
    return EntityManagerUtils.createEntityManager(properties, joyWebDataSource, ENTITY_PACKAGE);
  }

  @Primary
  @Bean(name = "joyWebTransactionManager")
  public PlatformTransactionManager joyWebTransactionManager(final EntityManagerFactory joyWebEntityManager) {
    return new JpaTransactionManager(joyWebEntityManager);
  }
}

package com.nhn.pebblewebback.infra.util;

import com.nhn.pebblewebback.exception.constant.ExceptionInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

public final class EntityManagerUtils {

  private EntityManagerUtils() {
    throw ExceptionInfo.NOT_ALLOWED_CONSTRUCTOR_CALL.exception();
  }

  public static LocalContainerEntityManagerFactoryBean createEntityManager(JpaProperties jpaProperties, DataSource dataSource, String packageToScan) {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan(packageToScan);
    applyJpaVendorAdapter(jpaProperties, em);
    applyJpaProperties(jpaProperties, em);
    return em;
  }

  private static void applyJpaVendorAdapter(JpaProperties jpaProperties, LocalContainerEntityManagerFactoryBean em) {
    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabase(Objects.requireNonNullElse(jpaProperties.getDatabase(), Database.MYSQL));
    em.setJpaVendorAdapter(vendorAdapter);
  }

  private static void applyJpaProperties(JpaProperties jpaProperties, LocalContainerEntityManagerFactoryBean em) {
    final Map<String, Object> properties = new HashMap<>(jpaProperties.getProperties());
    properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
    properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
    em.setJpaPropertyMap(properties);
  }
}

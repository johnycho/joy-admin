package com.joy.config.util;

import com.joy.config.exception.constant.ExceptionInfo;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;

public final class DataSourceBindUtils {

  private DataSourceBindUtils() {
    throw ExceptionInfo.NOT_ALLOWED_CONSTRUCTOR_CALL.exception();
  }

  public static DataSource bind(final Environment environment, final String propertyName) {
    final Map<?, ?> properties = Binder.get(environment).bind(propertyName, Map.class).get();
    return bind(DataSourceBuilder.create().type(HikariDataSource.class).build(), properties);
  }

  private static DataSource bind(final DataSource result, final Map<?, ?> properties) {
    final ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
    final ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
    aliases.addAliases("url", "jdbc-url");
    aliases.addAliases("username", "user");
    final Binder binder = new Binder(source.withAliases(aliases));
    binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
    return result;
  }
}

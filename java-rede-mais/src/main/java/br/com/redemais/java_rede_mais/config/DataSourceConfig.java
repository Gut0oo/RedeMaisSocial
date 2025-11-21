package br.com.redemais.java_rede_mais.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.hikari.maximum-pool-size:10}")
    private int maxPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle:2}")
    private int minIdle;

    @Value("${spring.datasource.hikari.idle-timeout:600000}") // 10min
    private long idleTimeout;

    @Value("${spring.datasource.hikari.connection-timeout:30000}") // 30s
    private long connectionTimeout;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);

        // Driver do MySQL (novo conector)
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Configurações de pool
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minIdle);
        config.setIdleTimeout(idleTimeout);
        config.setConnectionTimeout(connectionTimeout);

        // Opcional: nome do pool pra facilitar monitoramento
        config.setPoolName("MyAppHikariPool");

        return new HikariDataSource(config);
    }
}

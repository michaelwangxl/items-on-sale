package com.michael.test.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author michaelwang on 2021-03-11
 */
@Configuration
@ComponentScan
@EntityScan("com.michael.test.domains")
@EnableJpaRepositories("com.michael.test.repository")
public class DBConfig {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Bean
    public DataSource dataSource() {
        logger.info("dataSource() invoked");

        DataSource dataSource = (new EmbeddedDatabaseBuilder())
                .addScript("classpath:testdb/schema.sql")
                .addScript("classpath:testdb/data.sql").build();

        logger.info("dataSource = " + dataSource);

        // Sanity check
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> product = jdbcTemplate.queryForList("SELECT * FROM PRODUCT");
        logger.info("System has " + product.size() + " products");

        return dataSource;
    }
}

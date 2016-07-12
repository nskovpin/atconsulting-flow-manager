package ru.atconsulting.bigdata.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by NSkovpin on 21.06.2016.
 */
@Configuration
@EnableJpaRepositories(basePackages = "ru.atconsulting.bigdata.repo")
public class JpaRepositoryConfiguration {
}

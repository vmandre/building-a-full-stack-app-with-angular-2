package com.linkedin.learning.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("com.linkedin.learning.repository")
@EnableTransactionManagement
@Component
public class DatabaseConfig {
	
}

package com.agoldberg.hercules.config;

import com.agoldberg.hercules.service.AuditorAwareService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JPAConfigurer {
    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareService();
    }
}

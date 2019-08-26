package com.nanshen.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCrosConfig {

    @Bean
    public CorsFilter corsFilter(){
        //添加配置信息
        CorsConfiguration corsConfiguration=new CorsConfiguration();
//        corsConfiguration.setAllowedOrigins();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addExposedHeader("*");
        //添加映射路径
        UrlBasedCorsConfigurationSource configSource=new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(configSource);
    }
}

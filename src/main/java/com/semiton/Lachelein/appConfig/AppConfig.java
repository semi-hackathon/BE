package com.semiton.Lachelein.appConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Configuration
public class AppConfig {
    // HttpClient는 애플리케이션 전역에서 재사용하는 것이 좋습니다.
    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    // JSON <-> Java 객체 변환을 위한 ObjectMapper
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

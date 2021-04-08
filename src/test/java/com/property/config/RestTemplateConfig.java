package com.property.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {
 
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }
 
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
//       SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
// The previous line is commented out by Spring's own implementation. The following is the implementation after relying on the httpclient package.
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return factory;
    }
}

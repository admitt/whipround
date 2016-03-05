package ch.whip.round;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {
    @Bean
    RestOperations createRestClient() {
        return new RestTemplate();
    }
}

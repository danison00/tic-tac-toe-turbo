package com.dandev.tictactoeturbo.infra.scheduling;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class HealthService {

    private RestTemplate template = new RestTemplate();
    @Value("${spring.application.health.check.endpoint}")
    private String enpoint;
    private Logger LOGGER = Logger.getLogger(HealthService.class.getName());

    @Scheduled(fixedRate = 600000)
    public void healthRequest() {
        try {
            String response = template.getForObject(enpoint, String.class);
            LOGGER.info(response);
        } catch (Throwable e) {
            LOGGER.warning(e.toString());
        }

    }
}

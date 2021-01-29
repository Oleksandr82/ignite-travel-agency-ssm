package tech.travel.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TravelAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelAgentApplication.class, args);
    }

}

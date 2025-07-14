package sp.informationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InformationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InformationServiceApplication.class, args);
    }

}

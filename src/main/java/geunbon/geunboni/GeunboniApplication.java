package geunbon.geunboni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GeunboniApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeunboniApplication.class, args);
    }

}

package be.pekket.cocubo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CocuboApplication {

    public static void main( String[] args ) {
        SpringApplication.run(CocuboApplication.class, args);
    }

}

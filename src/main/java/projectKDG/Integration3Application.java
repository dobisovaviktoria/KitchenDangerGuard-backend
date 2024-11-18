package projectKDG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "projectKDG")
public class Integration3Application {

    public static void main(String[] args) {

        SpringApplication.run(Integration3Application.class, args);
    }
}

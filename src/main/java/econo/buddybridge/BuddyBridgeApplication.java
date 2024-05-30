package econo.buddybridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // @CreatedDate, @LastModifiedDate
public class BuddyBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuddyBridgeApplication.class, args);
    }

}

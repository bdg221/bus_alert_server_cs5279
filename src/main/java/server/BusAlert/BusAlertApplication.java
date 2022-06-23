package server.BusAlert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BusAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusAlertApplication.class, args);
	}

}

package space.industock.industrial_stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IndustrialStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndustrialStockApplication.class, args);
	}

}

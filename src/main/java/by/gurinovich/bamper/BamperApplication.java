package by.gurinovich.bamper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BamperApplication {

	public static void main(String[] args) {
		SpringApplication.run(BamperApplication.class, args);
	}

}

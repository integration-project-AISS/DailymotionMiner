package aiss.dailymotionminer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DailymotionminerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailymotionminerApplication.class, args);
	}
	// esto se pone para que funcionen los @Autowried y los @Component porque si no spring no sabe de donde sacar esos datos
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}

package ru.snsin.cakefactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.snsin.cakefactory.order.PayPalConfig;

@SpringBootApplication
@EnableConfigurationProperties(PayPalConfig.class)
public class CakeFactoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakeFactoryApplication.class, args);
	}

}

package com.nineleaps.breakTheHunger;

import com.nineleaps.breakTheHunger.commonConfig.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class BreakTheHungerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BreakTheHungerApplication.class, args);
	}
}

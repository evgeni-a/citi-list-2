package com.anchuk.citylist;

import com.anchuk.citylist.config.RsaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaProperties.class)
public class CityListApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityListApplication.class, args);
	}

}

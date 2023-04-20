package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestaurantManagerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagerServerApplication.class, args);
	}

}

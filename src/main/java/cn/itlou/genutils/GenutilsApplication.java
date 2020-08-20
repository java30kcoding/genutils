package cn.itlou.genutils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GenutilsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenutilsApplication.class, args);
	}

}

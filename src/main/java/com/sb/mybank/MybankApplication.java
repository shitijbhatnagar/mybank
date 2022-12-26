package com.sb.mybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MybankApplication {

	public static void main(String[] args)
	{
		//Note that the property spring.jpa.hibernate.ddl-auto needs to be set to 'validate' for data-dev.sql to run
		SpringApplication.run(MybankApplication.class, args);
	}

}

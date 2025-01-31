package com.sb.mybank;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptableProperties
@SpringBootApplication
public class MybankApplication {

	public static void main(String[] args)
	{
		//Note that the property spring.jpa.hibernate.ddl-auto needs to be set to 'validate' for data-dev.sql to run
		SpringApplication.run(MybankApplication.class, args);
	}

}

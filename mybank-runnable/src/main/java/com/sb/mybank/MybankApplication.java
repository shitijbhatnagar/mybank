package com.sb.mybank;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@EnableEncryptableProperties
@SpringBootApplication
//@ComponentScan(basePackages={"com.sb.mybank.config", "com.sb.mybank.constants",
//		"com.sb.mybank.dto",
//		"com.sb.mybank.exception",
//		"com.sb.mybank",
//		"com.sb.mybank.web",
//		"com.sb.mybank.model",
//		"com.sb.mybank.repository",
//		"com.sb.mybank.service",
//		"com.sb.mybank.util"})
public class MybankApplication {

	public static void main(String[] args)
	{
		//Note that the property spring.jpa.hibernate.ddl-auto needs to be set to 'validate' for data-dev.sql to run
		SpringApplication.run(MybankApplication.class, args);
	}

}

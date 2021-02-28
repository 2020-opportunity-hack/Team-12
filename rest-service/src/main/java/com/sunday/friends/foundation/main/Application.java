package com.sunday.friends.foundation.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/**
 * The main Function
 * Run the Application to Start the Server on local machine
 * default Port 8080
 * SAmple API : http://0.0.0.0:8080/admin/fetchUsers
 * @author  Mahapatra Manas, Roy Abhinav
 * @version 1.0
 * @since   11-20-2020
 */
@SpringBootApplication
@EntityScan(basePackages="com.sunday.friends.foundation.model")
@ComponentScan({"com.sunday.friends.foundation.controller","com.sunday.friends.foundation.service"})
@EnableJpaRepositories("com.sunday.friends.foundation.repository")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

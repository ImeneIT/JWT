package com.example.JWTProject;

import com.example.JWTProject.domain.AppUser;
import com.example.JWTProject.domain.Role;
import com.example.JWTProject.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;

@SpringBootApplication
public class JwtProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtProjectApplication.class, args);
		System.out.println("hii");
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
 CommandLineRunner run(UserService userService){
return args -> {
	userService.saveRole(new Role(null,"ROLE_USER"));
	userService.saveRole(new Role(null,"ROLE_MANAGER"));
	userService.saveRole(new Role(null,"ROLE_ADMIN"));
	userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
	userService.saveUser(new AppUser(null,"Imene Tiabi","Imenus","1234",new ArrayList<>()));
	userService.saveUser(new AppUser(null,"Will Smith","Will","1234",new ArrayList<>()));
	userService.saveUser(new AppUser(null,"San Smith","San","1234",new ArrayList<>()));
	userService.saveUser(new AppUser(null,"Jhonny Depp","Jhonny","1234",new ArrayList<>()));
	userService.addRoleToUser("Imenus","ROLE_USER");
	userService.addRoleToUser("Will","ROLE_MANAGER");
	userService.addRoleToUser("San","ROLE_ADMIN");
	userService.addRoleToUser("Jhonny","ROLE_SUPER_ADMIN");

};
}
}

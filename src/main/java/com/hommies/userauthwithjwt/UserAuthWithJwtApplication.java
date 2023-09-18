package com.hommies.userauthwithjwt;

import com.hommies.userauthwithjwt.dto.RegisterRequest;
import com.hommies.userauthwithjwt.model.Role;
import com.hommies.userauthwithjwt.model.User;
import com.hommies.userauthwithjwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserAuthWithJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthWithJwtApplication.class, args);
	}



	@Bean
	public CommandLineRunner commandLineRunner(UserService service){
		return args -> {

			var admin = RegisterRequest.builder()
					.firstName("Admin")
					.lastName("Admin")
					.email("aaaaa@gmail.com")
					.password("password")
					.role(Role.ADMIN)
					.build();

			System.out.println("ADMIN Token :: " + service.register(admin).getToken());



			var manager = RegisterRequest.builder()
					.firstName("Manager")
					.lastName("Manager")
					.email("mmmmm@gmail.com")
					.password("password")
					.role(Role.MANAGER)
					.build();

			System.out.println("MANAGER Token :: " + service.register(manager).getToken());

		};
	}
}

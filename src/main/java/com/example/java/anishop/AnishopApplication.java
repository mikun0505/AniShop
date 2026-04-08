package com.example.java.anishop;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.java.anishop.exception.AppException;
import com.example.java.anishop.repository.RoleRepository;
import com.example.java.anishop.repository.UserRepository;
import com.example.java.anishop.repository.entity.Roles;
import com.example.java.anishop.repository.entity.Users;

@SpringBootApplication
public class AnishopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnishopApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(	UserRepository userRepo,
		PasswordEncoder PasswordEncoder,
		RoleRepository roleRepository
	){
		return args -> {
			if(userRepo.findByEmail("duoc@gmail.com")==null){
				if(roleRepository.findByName("ROLE_ADMIN").isEmpty()){
					Roles role=new Roles();
					role.setName("ROLE_ADMIN");
					roleRepository.save(role);
				}
				Roles rolesAdmin=roleRepository.findByName("ROLE_ADMIN")
					.orElseThrow(() -> new AppException("Không tìm thấy",500));

				Users user=Users.builder()
				.email("duoc@gmail.com")
				.password(PasswordEncoder.encode("cuocAdmin"))
				.roles(Set.of(rolesAdmin))
				.build();
				
				userRepo.save(user);
			}
		};
	}
}

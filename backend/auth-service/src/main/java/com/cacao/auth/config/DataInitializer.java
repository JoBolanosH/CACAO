package com.cacao.auth.config;

import com.cacao.auth.model.User;
import com.cacao.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(@Autowired UserRepository userRepository, 
                                         @Autowired PasswordEncoder passwordEncoder) {
        return args -> {
            // Crear usuario de prueba si no existe
            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin123"));
                adminUser.setEmail("admin@cacao.local");
                adminUser.setIsActive(true);
                userRepository.save(adminUser);
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                User regularUser = new User();
                regularUser.setUsername("user");
                regularUser.setPassword(passwordEncoder.encode("user123"));
                regularUser.setEmail("user@cacao.local");
                regularUser.setIsActive(true);
                userRepository.save(regularUser);
            }

            System.out.println("✅ Base de datos inicializada con usuarios de prueba");
            System.out.println("📌 Usuario: admin | Contraseña: admin123");
            System.out.println("📌 Usuario: user | Contraseña: user123");
        };
    }
}

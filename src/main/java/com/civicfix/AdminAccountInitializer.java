
package com.civicfix;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminAccountInitializer {

    @Bean
    public CommandLineRunner createAdminAccount(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            @Value("${civicfix.admin.username}") String adminUsername,
            @Value("${civicfix.admin.password}") String adminPassword) {

        return args -> {
            AppUser existingAdmin = appUserRepository.findByUsername(adminUsername)
                    .orElse(null);

            if (existingAdmin == null) {
                AppUser admin = new AppUser();

                admin.setFullName("CivicFix Administrator");
                admin.setUsername(adminUsername);
                admin.setEmail("admin@civicfix.local");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole("ADMIN");

                appUserRepository.save(admin);

                System.out.println("CivicFix admin account created.");
            } else if (!"ADMIN".equals(existingAdmin.getRole())) {
                throw new IllegalStateException(
                        "The configured admin username already belongs "
                                + "to a non-admin account. Change "
                                + "civicfix.admin.username in application.properties.");
            }
        };
    }
}

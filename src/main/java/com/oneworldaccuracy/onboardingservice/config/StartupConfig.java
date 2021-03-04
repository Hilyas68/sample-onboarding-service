package com.oneworldaccuracy.onboardingservice.config;

import com.oneworldaccuracy.onboardingservice.model.User;
import com.oneworldaccuracy.onboardingservice.repository.UserRepository;
import com.oneworldaccuracy.onboardingservice.vo.enums.Roles;
import com.oneworldaccuracy.onboardingservice.vo.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class StartupConfig {

    @Autowired
    UserRepository repository;

    private static final Logger logger = Logger.getLogger(StartupConfig.class.getName());


    @EventListener
    public void seed(ContextRefreshedEvent event) {
        try {
            if (!repository.existsByEmail("admin@oneworldaccuracy.com")) {

                User user = new User(0L, "MR", "Hassan", "LastName", "admin@oneworldaccuracy.com", "08022318882", "", Roles.ADMIN, LocalDateTime.now(),
                        null, null, true, false, Status.VERIFIED, null);

                repository.save(user);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception Seeding Database: " + e.getMessage());
        }
    }
}

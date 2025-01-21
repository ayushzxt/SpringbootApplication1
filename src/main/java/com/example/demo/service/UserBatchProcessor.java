package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.StatusCodeMainRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class UserBatchProcessor {

    private final UserRepository userRepository;
    private final StatusCodeMainRepository statusCodeMainRepository;

    public UserBatchProcessor(UserRepository userRepository, StatusCodeMainRepository statusCodeMainRepository) {
        this.userRepository = userRepository;
        this.statusCodeMainRepository = statusCodeMainRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void updateUserAgeAndStatus() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            // Calculate the age
            int age = Period.between(user.getDob(), LocalDate.now()).getYears();
            user.setAge(age);

            // Determine the status based on age
            String status;
            if (age < 18) {
                status = "MINOR";
            } else if (age < 60) {
                status = "ADULT";
            } else {
                status = "SENIOR";
            }
            user.setStatus(status);

            // Save the updated user
            userRepository.save(user);
        }
    }

    @Scheduled(cron = "*/10 * * * * ?") // Runs every 10 seconds
    public void printHelloWorld() {
        System.out.println("Hello World");
    }
}
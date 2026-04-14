package com.example.eventsportal.config;

import com.example.eventsportal.model.StudentEvent;
import com.example.eventsportal.repository.StudentEventRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedEvents(StudentEventRepository eventRepository) {
        return args -> {
            if (eventRepository.count() > 0) {
                return;
            }

            eventRepository.saveAll(List.of(
                    new StudentEvent(
                            "Conference Java moderne",
                            "Une conference sur Spring Boot, securite et architecture.",
                            "Conference",
                            "Salle A1",
                            LocalDate.now().plusDays(3)),
                    new StudentEvent(
                            "Atelier IA pratique",
                            "Atelier pour decouvrir les bases de l'intelligence artificielle.",
                            "Atelier",
                            "Lab Info 2",
                            LocalDate.now().plusDays(7)),
                    new StudentEvent(
                            "Soiree des clubs",
                            "Rencontrez les clubs et associations etudiantes.",
                            "Soiree",
                            "Campus Central",
                            LocalDate.now().plusDays(12)),
                    new StudentEvent(
                            "Web Security Night",
                            "Talks and demos about secure web development.",
                            "Conference",
                            "Room B4",
                            LocalDate.now().plusDays(16)),
                    new StudentEvent(
                            "Design Thinking Workshop",
                            "Hands-on workshop on product ideation and teamwork.",
                            "Workshop",
                            "Innovation Space",
                            LocalDate.now().plusDays(20))));
        };
    }
}


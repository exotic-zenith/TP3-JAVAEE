package com.example.eventsportal.service;

import com.example.eventsportal.model.Registration;
import com.example.eventsportal.model.StudentEvent;
import com.example.eventsportal.model.UserAccount;
import com.example.eventsportal.repository.RegistrationRepository;
import com.example.eventsportal.repository.StudentEventRepository;
import com.example.eventsportal.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final StudentEventRepository eventRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                               UserRepository userRepository,
                               StudentEventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public void register(String email, Long eventId) {
        try {
            logger.info("Attempting to register user {} for event {}", email, eventId);
            
            UserAccount user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
            
            StudentEvent event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

            if (registrationRepository.existsByUserAndEvent(user, event)) {
                logger.warn("User {} already registered for event {}", email, eventId);
                throw new IllegalStateException("User already registered for this event");
            }

            Registration registration = new Registration(user, event, LocalDateTime.now());
            registrationRepository.save(registration);
            logger.info("Successfully registered user {} for event {}", email, eventId);
        } catch (Exception e) {
            logger.error("Error registering user {} for event {}: {}", email, eventId, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void unregister(String email, Long eventId) {
        try {
            logger.info("Attempting to unregister user {} from event {}", email, eventId);
            
            UserAccount user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
            
            StudentEvent event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

            registrationRepository.deleteByUserAndEvent(user, event);
            logger.info("Successfully unregistered user {} from event {}", email, eventId);
        } catch (Exception e) {
            logger.error("Error unregistering user {} from event {}: {}", email, eventId, e.getMessage(), e);
            throw e;
        }
    }

    public List<Registration> getMyRegistrations(String email) {
        try {
            logger.info("Fetching registrations for user {}", email);
            
            UserAccount user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
            
            List<Registration> registrations = registrationRepository.findByUserOrderByRegisteredAtDesc(user);
            logger.info("Found {} registrations for user {}", registrations.size(), email);
            return registrations;
        } catch (Exception e) {
            logger.error("Error fetching registrations for user {}: {}", email, e.getMessage(), e);
            throw e;
        }
    }
}


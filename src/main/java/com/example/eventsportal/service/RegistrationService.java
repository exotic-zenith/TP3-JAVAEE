package com.example.eventsportal.service;

import com.example.eventsportal.model.Registration;
import com.example.eventsportal.model.StudentEvent;
import com.example.eventsportal.model.UserAccount;
import com.example.eventsportal.repository.RegistrationRepository;
import com.example.eventsportal.repository.StudentEventRepository;
import com.example.eventsportal.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

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

    public void register(String email, Long eventId) {
        UserAccount user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        StudentEvent event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (registrationRepository.existsByUserAndEvent(user, event)) {
            throw new IllegalStateException("User already registered for this event");
        }

        Registration registration = new Registration(user, event, LocalDateTime.now());
        registrationRepository.save(registration);
    }

    public void unregister(String email, Long eventId) {
        UserAccount user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        StudentEvent event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        registrationRepository.deleteByUserAndEvent(user, event);
    }

    public List<Registration> getMyRegistrations(String email) {
        UserAccount user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return registrationRepository.findByUserOrderByRegisteredAtDesc(user);
    }
}


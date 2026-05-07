package com.example.eventsportal.controller;

import com.example.eventsportal.model.StudentEvent;
import com.example.eventsportal.repository.RegistrationRepository;
import com.example.eventsportal.repository.UserRepository;
import com.example.eventsportal.service.EventService;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    private final EventService eventService;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;

    public EventController(EventService eventService,
                          UserRepository userRepository,
                          RegistrationRepository registrationRepository) {
        this.eventService = eventService;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
    }

    @GetMapping("/events")
    public String events(@RequestParam(name = "q", required = false) String query,
                         Authentication authentication,
                         Model model) {
        List<StudentEvent> events = eventService.searchEvents(query);
        model.addAttribute("events", events);
        model.addAttribute("query", query == null ? "" : query);

        String email = authentication.getName();
        userRepository.findByEmail(email).ifPresent(user -> {
            model.addAttribute("displayName", user.getDisplayName());

            // Build set of registered event IDs for this user
            Set<Long> registeredEventIds = registrationRepository
                    .findByUserOrderByRegisteredAtDesc(user)
                    .stream()
                    .map(reg -> reg.getEvent().getId())
                    .collect(java.util.stream.Collectors.toSet());

            model.addAttribute("registeredEventIds", registeredEventIds);
        });

        return "events";
    }
}


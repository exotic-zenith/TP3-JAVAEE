package com.example.eventsportal.controller;

import com.example.eventsportal.model.StudentEvent;
import com.example.eventsportal.repository.UserRepository;
import com.example.eventsportal.service.EventService;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    private final EventService eventService;
    private final UserRepository userRepository;

    public EventController(EventService eventService, UserRepository userRepository) {
        this.eventService = eventService;
        this.userRepository = userRepository;
    }

    @GetMapping("/events")
    public String events(@RequestParam(name = "q", required = false) String query,
                         Authentication authentication,
                         Model model) {
        List<StudentEvent> events = eventService.searchEvents(query);
        model.addAttribute("events", events);
        model.addAttribute("query", query == null ? "" : query);

        String email = authentication.getName();
        userRepository.findByEmail(email).ifPresent(user -> model.addAttribute("displayName", user.getDisplayName()));

        return "events";
    }
}


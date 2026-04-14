package com.example.eventsportal.service;

import com.example.eventsportal.model.StudentEvent;
import com.example.eventsportal.repository.StudentEventRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final StudentEventRepository studentEventRepository;

    public EventService(StudentEventRepository studentEventRepository) {
        this.studentEventRepository = studentEventRepository;
    }

    public List<StudentEvent> searchEvents(String query) {
        if (query == null || query.isBlank()) {
            return studentEventRepository.findAllByOrderByEventDateAsc();
        }
        String normalized = query.trim();
        return studentEventRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByEventDateAsc(
                normalized, normalized);
    }
}


package com.example.eventsportal.repository;

import com.example.eventsportal.model.StudentEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEventRepository extends JpaRepository<StudentEvent, Long> {

    List<StudentEvent> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByEventDateAsc(
            String titleQuery, String descriptionQuery);

    List<StudentEvent> findAllByOrderByEventDateAsc();
}


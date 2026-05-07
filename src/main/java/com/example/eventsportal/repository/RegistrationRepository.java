package com.example.eventsportal.repository;

import com.example.eventsportal.model.Registration;
import com.example.eventsportal.model.StudentEvent;
import com.example.eventsportal.model.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByUserAndEvent(UserAccount user, StudentEvent event);

    List<Registration> findByUserOrderByRegisteredAtDesc(UserAccount user);

    @Transactional
    void deleteByUserAndEvent(UserAccount user, StudentEvent event);
}


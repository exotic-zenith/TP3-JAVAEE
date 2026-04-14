package com.example.eventsportal.service;

import com.example.eventsportal.dto.RegisterRequest;
import com.example.eventsportal.model.UserAccount;
import com.example.eventsportal.repository.UserRepository;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private static final Pattern STRONG_PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerNewUser(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("password.mismatch");
        }

        if (!isStrongPassword(request.getPassword())) {
            throw new IllegalArgumentException("password.weak");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("email.exists");
        }

        UserAccount user = new UserAccount();
        user.setDisplayName(request.getDisplayName().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }

    public static boolean isStrongPassword(String rawPassword) {
        return rawPassword != null && STRONG_PASSWORD_PATTERN.matcher(rawPassword).matches();
    }
}


package com.example.eventsportal.controller;

import com.example.eventsportal.model.Registration;
import com.example.eventsportal.service.RegistrationService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/events/{id}/register")
    public String register(@PathVariable Long id,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        try {
            logger.info("Register request for event {} by user {}", id, authentication.getName());
            registrationService.register(authentication.getName(), id);
            redirectAttributes.addFlashAttribute("successMessage", "registration.success");
            logger.info("Successfully handled register request");
        } catch (IllegalStateException e) {
            logger.warn("Already registered: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "registration.already");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "registration.error");
        } catch (Exception e) {
            logger.error("Unexpected error during registration: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "registration.error");
        }
        return "redirect:/events";
    }

    @PostMapping("/events/{id}/unregister")
    public String unregister(@PathVariable Long id,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        try {
            logger.info("Unregister request for event {} by user {}", id, authentication.getName());
            registrationService.unregister(authentication.getName(), id);
            redirectAttributes.addFlashAttribute("successMessage", "registration.cancelled");
            logger.info("Successfully handled unregister request");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "registration.error");
        } catch (Exception e) {
            logger.error("Unexpected error during unregistration: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "registration.error");
        }
        return "redirect:/events";
    }

    @GetMapping("/my-registrations")
    public String myRegistrations(Authentication authentication,
                                  Model model) {
        try {
            logger.info("Fetching registrations for user {}", authentication.getName());
            List<Registration> registrations = registrationService.getMyRegistrations(authentication.getName());
            model.addAttribute("registrations", registrations);
            logger.info("Found {} registrations", registrations.size());
        } catch (Exception e) {
            logger.error("Error fetching registrations: {}", e.getMessage(), e);
            model.addAttribute("registrations", List.of());
        }
        return "my-registrations";
    }
}


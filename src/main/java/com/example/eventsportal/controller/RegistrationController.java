package com.example.eventsportal.controller;

import com.example.eventsportal.model.Registration;
import com.example.eventsportal.service.RegistrationService;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/events/{id}/register")
    public String register(@PathVariable Long id,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        try {
            registrationService.register(authentication.getName(), id);
            redirectAttributes.addFlashAttribute("successMessage", "registration.success");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "registration.already");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "registration.error");
        }
        return "redirect:/events";
    }

    @PostMapping("/events/{id}/unregister")
    public String unregister(@PathVariable Long id,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        try {
            registrationService.unregister(authentication.getName(), id);
            redirectAttributes.addFlashAttribute("successMessage", "registration.cancelled");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "registration.error");
        }
        return "redirect:/events";
    }

    @GetMapping("/my-registrations")
    public String myRegistrations(Authentication authentication,
                                  Model model) {
        try {
            List<Registration> registrations = registrationService.getMyRegistrations(authentication.getName());
            model.addAttribute("registrations", registrations);
        } catch (Exception e) {
            model.addAttribute("registrations", List.of());
        }
        return "my-registrations";
    }
}


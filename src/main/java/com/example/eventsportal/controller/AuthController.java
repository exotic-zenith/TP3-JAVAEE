package com.example.eventsportal.controller;

import com.example.eventsportal.dto.RegisterRequest;
import com.example.eventsportal.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final AuthService authService;
    private final MessageSource messageSource;

    public AuthController(AuthService authService, MessageSource messageSource) {
        this.authService = authService;
        this.messageSource = messageSource;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("registerRequest")) {
            model.addAttribute("registerRequest", new RegisterRequest());
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            authService.registerNewUser(registerRequest);
        } catch (IllegalArgumentException ex) {
            String key = ex.getMessage();
            String message = messageSource.getMessage(key, null, key, LocaleContextHolder.getLocale());
            if ("password.mismatch".equals(key)) {
                bindingResult.rejectValue("confirmPassword", "error.registerRequest", message);
            } else if ("password.weak".equals(key)) {
                bindingResult.rejectValue("password", "error.registerRequest", message);
            } else {
                bindingResult.rejectValue("email", "error.registerRequest", message);
            }
            return "register";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                messageSource.getMessage("register.success", null, LocaleContextHolder.getLocale()));
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}

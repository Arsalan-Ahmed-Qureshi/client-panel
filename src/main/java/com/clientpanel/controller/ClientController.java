package com.clientpanel.controller;

import com.clientpanel.model.User;
import com.clientpanel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final UserService userService;
    private static final int PAGE_SIZE = 10;

    @GetMapping
    public String listClients(
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<User> clients = userService.getAllUsers(pageable);

        model.addAttribute("clients", clients);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", clients.getTotalPages());
        model.addAttribute("totalElements", clients.getTotalElements());

        return "client/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "client/add";
    }

    @PostMapping("/add")
    public String addClient(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "client/add";
        }

        // Check for duplicate email
        if (userService.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Email is already in use");
            return "client/add";
        }

        // Check for duplicate mobile
        if (userService.mobileExists(user.getMobile())) {
            bindingResult.rejectValue("mobile", "error.mobile", "Mobile number is already in use");
            return "client/add";
        }

        // Check for duplicate phone number ID
        if (userService.phoneNumberIdExists(user.getPhoneNumberId())) {
            bindingResult.rejectValue("phoneNumberId", "error.phoneNumberId", "Phone number ID is already in use");
            return "client/add";
        }

        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Client added successfully!");
            return "redirect:/clients";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding client: " + e.getMessage());
            return "client/add";
        }
    }

    @GetMapping("/edit/{clientId}")
    public String showEditForm(
            @PathVariable String clientId,
            Model model) {
        User user = userService.getUserById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
        model.addAttribute("user", user);
        return "client/edit";
    }

    @PostMapping("/edit/{clientId}")
    public String updateClient(
            @PathVariable String clientId,
            @ModelAttribute("user") User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        System.out.println("📝 Update request for client: " + clientId);
        
        // Validate individual fields manually (skip password validation for edit)
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            bindingResult.rejectValue("email", "error.email", "Email is required");
        } else if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            bindingResult.rejectValue("email", "error.email", "Email should be valid");
        }
        
        if (user.getName() == null || user.getName().isEmpty()) {
            bindingResult.rejectValue("name", "error.name", "Name is required");
        }
        
        if (user.getMobile() == null || user.getMobile().isEmpty()) {
            bindingResult.rejectValue("mobile", "error.mobile", "Mobile number is required");
        } else if (!user.getMobile().matches("^[0-9]{10,15}$")) {
            bindingResult.rejectValue("mobile", "error.mobile", "Mobile number must be numeric between 10 and 15 digits");
        }
        
        if (user.getPhoneNumberId() == null || user.getPhoneNumberId().isEmpty()) {
            bindingResult.rejectValue("phoneNumberId", "error.phoneNumberId", "Phone number ID is required");
        } else if (!user.getPhoneNumberId().matches("^[0-9]{15}$")) {
            bindingResult.rejectValue("phoneNumberId", "error.phoneNumberId", "Phone number ID must be exactly 15 numeric digits");
        }
        
        if (bindingResult.hasErrors()) {
            System.out.println("❌ Validation errors found: " + bindingResult.getErrorCount());
            bindingResult.getAllErrors().forEach(error -> System.out.println("  - " + error.getDefaultMessage()));
            return "client/edit";
        }

        // Check for duplicate email (excluding current user)
        userService.getUserByEmail(user.getEmail()).ifPresent(existingUser -> {
            if (!existingUser.getClientId().equals(clientId)) {
                System.out.println("❌ Email already in use: " + user.getEmail());
                bindingResult.rejectValue("email", "error.email", "Email is already in use");
            }
        });

        // Check for duplicate mobile (excluding current user)
        if (!userService.isMobileUniqueExcluding(user.getMobile(), clientId)) {
            System.out.println("❌ Mobile already in use: " + user.getMobile());
            bindingResult.rejectValue("mobile", "error.mobile", "Mobile number is already in use");
        }

        // Check for duplicate phone number ID (excluding current user)
        if (!userService.isPhoneNumberIdUniqueExcluding(user.getPhoneNumberId(), clientId)) {
            System.out.println("❌ Phone number ID already in use: " + user.getPhoneNumberId());
            bindingResult.rejectValue("phoneNumberId", "error.phoneNumberId", "Phone number ID is already in use");
        }

        if (bindingResult.hasErrors()) {
            System.out.println("❌ Duplicate field errors found");
            return "client/edit";
        }

        try {
            userService.updateUser(clientId, user);
            redirectAttributes.addFlashAttribute("successMessage", "Client updated successfully!");
            return "redirect:/clients";
        } catch (Exception e) {
            System.err.println("❌ Error updating client: " + e.getMessage());
            model.addAttribute("errorMessage", "Error updating client: " + e.getMessage());
            return "client/edit";
        }
    }

    @PostMapping("/delete/{clientId}")
    public String deleteClient(
            @PathVariable String clientId,
            RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(clientId);
            redirectAttributes.addFlashAttribute("successMessage", "Client deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting client: " + e.getMessage());
        }
        return "redirect:/clients";
    }
}

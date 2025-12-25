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
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private static final int PAGE_SIZE = 10;

    @GetMapping
    public String dashboard(Model model) {
        long activeUsers = userService.getActiveUsersCount();
        long inactiveUsers = userService.getInactiveUsersCount();
        long totalUsers = userService.getTotalUsersCount();

        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("inactiveUsers", inactiveUsers);
        model.addAttribute("totalUsers", totalUsers);

        return "dashboard/dashboard";
    }
}

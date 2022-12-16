package com.example.bank.controllers;

import com.example.bank.models.Card;
import com.example.bank.models.ClientDetails;
import com.example.bank.models.TempRegistration;
import com.example.bank.models.User;
import com.example.bank.repositories.UserRepo;
import com.example.bank.services.CardService;
import com.example.bank.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final CardService cardService;
    private final UserRepo userRepo;

    @GetMapping
    public String getPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() != "anonymousUser") {
            return "redirect:/";
        }
        model.addAttribute("tempRegistration", new TempRegistration());
        return "registration";
    }

    @PostMapping
    public String registration(TempRegistration temp, Map<String, Object> model) {
        Card card = cardService.findCardByNumber(temp.getCardNumber());
        if (card == null) {
            model.put("message", "Карта не найдена");
            return "registration";
        }
        ClientDetails client = card.getClient();
        User user = userRepo.findByUsername(client.getEmail());
        if (user != null) {
            model.put("message", "Пользователь существует");
            return "registration";
        }
        userService.save(temp);
        return "redirect:/registration?success";
    }
}

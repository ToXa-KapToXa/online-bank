package com.example.bank.controllers;

import com.example.bank.dto.TransactionDTO;
import com.example.bank.dto.enums.CardAccountEnum;
import com.example.bank.models.Card;
import com.example.bank.models.User;
import com.example.bank.services.AccountService;
import com.example.bank.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final CardService cardService;
    private final AccountService accountService;

    @GetMapping
    public String getMainPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("cards", user.getClientDetails().getCards());
        model.addAttribute("accounts", user.getClientDetails().getAccounts());
        return "client/client";
    }

    @GetMapping("/transaction")
    public String getTransactionPage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("transaction", new TransactionDTO());
        model.addAttribute("cardOrAccount", CardAccountEnum.values());
        model.addAttribute("cards", user.getClientDetails().getCards());
        model.addAttribute("accounts", user.getClientDetails().getAccounts());
        return "client/transaction";
    }

    @PutMapping("/transaction")
    public String transaction(TransactionDTO dto, Map<String, Object>  model){
        Card card = cardService.findCardByNumber(dto.getFromNumber());
        if (card.getMoney() >= dto.getSum()) {
            cardService.transfer(dto);
            return "redirect:/client";
        } else {
            model.put("message", "Недопустимая сумма");
            return "client/transaction";
        }
    }

    @PutMapping("/cards/{id}/block")
    public String blockCard(@PathVariable("id") long id){
        cardService.blockCard(id);
        return "redirect:/client";
    }

    @PutMapping("/account/{id}/block")
    public String blockAccount(@PathVariable("id") long id){
        accountService.blockAccount(id);
        return "redirect:/client";
    }
}

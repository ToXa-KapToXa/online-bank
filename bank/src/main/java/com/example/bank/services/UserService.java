package com.example.bank.services;

import com.example.bank.models.ClientDetails;
import com.example.bank.models.TempRegistration;
import com.example.bank.models.User;
import com.example.bank.models.enums.Role;
import com.example.bank.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CardService cardService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public String save(TempRegistration temp) {
        /*Card card = cardService.findCardByNumber(temp.getCardNumber());
        if (card == null) {
            model.put("message", "Карта не найдена");
            return "registration";
        }
        ClientDetails client = card.getClient();
        User user = userRepo.findByUsername(client.getEmail());
        if (user != null) {
            model.put("message", "Пользователь существует");
            return "registration";
        }*/

        User newUser = new User();
        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setPassword(passwordEncoder.encode(temp.getPassword()));
        ClientDetails newClient = cardService.findCardByNumber(temp.getCardNumber()).getClient();
        newUser.setUsername(newClient.getEmail());
        newUser.setClientDetails(newClient);
        newUser.setActive(true);
        userRepo.save(newUser);
        return "redirect:/login";



        /*TempRegistration tempByCard = tempRegistrationRepo.findTempRegistrationByCardNumber(temp.getCardNumber());
        if(tempByCard != null){
            tempRegistrationRepo.delete(temp);
        }
        TempRegistration reg = new TempRegistration();
        reg.setToken(UUID.randomUUID().toString());
        reg.setCardNumber(temp.getCardNumber());
        reg.setPassword(passwordEncoder.encode(temp.getPassword()));
        tempRegistrationRepo.save(reg);
        emailService.send(client.getEmail(),
                "Подтверждение регистрации",
                client.getLastname() + " " +
                        client.getFirstname() +
                        ", для подтверждения регистрации перейдите по ссылке\n" +
                        "http://localhost:8080/registration/approve/" + reg.getToken());
        return "redirect:/email_sent";*/
    }

    /*public String approveRegistration(String token) {
        Optional<TempRegistration> tempOptional = tempRegistrationRepo.findById(token);
        if (tempOptional.isEmpty()) {
            return "redirect:/token_not_found";
        }
        TempRegistration temp = tempOptional.get();
        User user = new User();
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(temp.getPassword());
        ClientDetails client = cardService.findCardByNumber(temp.getCardNumber()).getClient();
        user.setUsername(client.getEmail());
        user.setClientDetails(client);
        user.setActive(true);
        userRepo.save(user);
        tempRegistrationRepo.delete(temp);
        return "redirect:/login";
    }*/


}

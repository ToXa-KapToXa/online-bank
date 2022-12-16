package com.example.bank.services;

import com.example.bank.dto.TopUpDTO;
import com.example.bank.dto.TransactionDTO;
import com.example.bank.models.Account;
import com.example.bank.models.Card;
import com.example.bank.repositories.CardRepo;
import com.example.bank.repositories.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepo cardRepo;
    private final AccountRepo accountRepo;

    public Card findCardByNumber(String number) {
        return cardRepo.findCardByNumber(number);
    }

    public Card findCardById(Long id) {
        return cardRepo.findCardById(id);
    }

    public Account findAccountByName(String name) {
        return accountRepo.findAccountByName(name);
    }

    public void topUpCard(Long id, TopUpDTO dto){
        Card card = findCardById(id);
        card.setMoney(card.getMoney()+dto.getMoney());
        cardRepo.save(card);
    }

    public void transfer(TransactionDTO dto){
            if (dto.getFromCardOrAccount().equals("card")) {
                Card cardFrom = findCardByNumber(dto.getFromNumber().subSequence(0, 12).toString());
                if (dto.getToMeOrNotToMee().equals("false")) {
                    Card cardTo = findCardByNumber(dto.getToAnotherNumber());
                    cardTo.setMoney(cardTo.getMoney() + dto.getSum());
                    cardRepo.save(cardTo);
                } else {
                    if (dto.getToCardOrAccount().equals("card")){
                        Card cardTo = findCardByNumber(dto.getToNumber());
                        cardTo.setMoney(cardTo.getMoney() + dto.getSum());
                        cardRepo.save(cardTo);
                    } else {
                        Account accountTo = findAccountByName(dto.getToName());
                        accountTo.setMoney(accountTo.getMoney() + dto.getSum());
                        accountRepo.save(accountTo);
                    }
                }
                cardFrom.setMoney(cardFrom.getMoney() - dto.getSum());
                cardRepo.save(cardFrom);
            } else {
                Account accountFrom = findAccountByName(dto.getFromName());
                if (dto.getToMeOrNotToMee().equals("false")) {
                    Card cardTo = findCardByNumber(dto.getToAnotherNumber());
                    cardTo.setMoney(cardTo.getMoney() + dto.getSum());
                    cardRepo.save(cardTo);
                } else {
                    if (dto.getToCardOrAccount().equals("card")){
                        Card cardTo = findCardByNumber(dto.getToNumber());
                        cardTo.setMoney(cardTo.getMoney() + dto.getSum());
                        cardRepo.save(cardTo);
                    } else {
                        Account accountTo = findAccountByName(dto.getToName());
                        accountTo.setMoney(accountTo.getMoney() + dto.getSum());
                        accountRepo.save(accountTo);
                    }
                }
                accountFrom.setMoney(accountFrom.getMoney() - dto.getSum());
                accountRepo.save(accountFrom);
            }
    }

    /*public void transfer(TransactionDTO dto){
        System.out.println(dto.getFromNumber().subSequence(0, 12).toString() + "AAAAA");
        Card cardFrom = findCardByNumber(dto.getFromNumber().subSequence(0, 12).toString());
        Card cardTo = findCardByNumber(dto.getToAnotherNumber());
        cardTo.setMoney(cardTo.getMoney() + cardFrom.getMoney());
        cardFrom.setMoney(0);
        cardRepo.save(cardFrom);
        cardRepo.save(cardTo);
    }*/


    /*Account accountTo = findAccountByName(dto.getToName());
                    accountTo.setMoney(accountTo.getMoney() + cardFrom.getMoney());
                    cardFrom.setMoney(0);
                    accountRepo.save(accountTo);*/

    public void blockCard(Long id){
        Card card = findCardById(id);
        card.setActive(false);
        cardRepo.save(card);
    }

    public void unblockCard(Long id){
        Card card = findCardById(id);
        card.setActive(true);
        cardRepo.save(card);
    }
}

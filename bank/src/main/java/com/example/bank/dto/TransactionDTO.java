package com.example.bank.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private int money;
    private int sum;
    private String fromCardOrAccount;
    private String fromNumber;
    private String fromName;
    private boolean toMeOrNotToMe;
    private String toCardOrAccount;
    private String toNumber;
    private String toName;
    private String toAnotherNumber;

    public String getToMeOrNotToMee(){
        if (toMeOrNotToMe){
            return "true";
        } else {
            return "false";
        }
    }
}

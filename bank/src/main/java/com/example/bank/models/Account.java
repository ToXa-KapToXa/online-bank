package com.example.bank.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int money;
    private boolean active;

    @ManyToOne
    private AccountType accountType;

    @ManyToOne
    private ClientDetails client;

    public String getActive(){
        if (!active){
            return "false";
        } else {
            return "true";
        }
    }
}

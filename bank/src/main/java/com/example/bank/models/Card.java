package com.example.bank.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    private String cardHolder;
    private LocalDate validDate;
    private int cvv;
    private int money;
    private boolean active;

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

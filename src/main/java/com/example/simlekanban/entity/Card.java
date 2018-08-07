package com.example.simlekanban.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;
    private String description;
    private Float position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardlist_id")
    @JsonIgnore
    private CardList cardList;

    public String getDescription() {
        return description != null ? description : "";
    }
}

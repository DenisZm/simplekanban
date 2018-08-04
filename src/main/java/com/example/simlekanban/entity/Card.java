package com.example.simlekanban.entity;

import javax.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private Float position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardlist_id")
    private CardList cardList;

    public Long getId() {
        return id;
    }

    public Card setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Card setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Card setDescription(String description) {
        this.description = description;
        return this;
    }

    public Float getPosition() {
        return position;
    }

    public Card setPosition(Float position) {
        this.position = position;
        return this;
    }

    public CardList getCardList() {
        return cardList;
    }

    public Card setCardList(CardList cardList) {
        this.cardList = cardList;
        return this;
    }
}

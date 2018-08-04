package com.example.simlekanban.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class CardList {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Float position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    public Long getId() {
        return id;
    }

    public CardList setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CardList setName(String name) {
        this.name = name;
        return this;
    }

    public Float getPosition() {
        return position;
    }

    public CardList setPosition(Float position) {
        this.position = position;
        return this;
    }

    public Board getBoard() {
        return board;
    }

    public CardList setBoard(Board board) {
        this.board = board;
        return this;
    }
}

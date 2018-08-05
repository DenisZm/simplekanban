package com.example.simlekanban.dto;

import javax.validation.constraints.NotNull;

public class CardUpdate {
    @NotNull
    private String title;
    private String description;
    private Float position;
    private Long listId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPosition() {
        return position;
    }

    public void setPosition(Float position) {
        this.position = position;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }
}

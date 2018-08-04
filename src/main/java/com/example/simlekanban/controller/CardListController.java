package com.example.simlekanban.controller;

import com.example.simlekanban.entity.CardList;
import com.example.simlekanban.service.CardListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/lists")
public class CardListController {

    private final CardListService listService;

    public CardListController(CardListService listService) {
        this.listService = listService;
    }

    @GetMapping(path = "/{listId}")
    public ResponseEntity<CardList> getList(@PathVariable("listId") Long listId) {
        return ResponseEntity.ok(listService.getListById(listId));
    }

    @PutMapping(path = "/{listId}")
    public ResponseEntity<CardList> updateList(@PathVariable("listId") Long listId,
                                               @Valid @RequestBody CardList list) {
        return ResponseEntity.ok(listService.updateList(listId, list));
    }

    @DeleteMapping(path = "/{listId}")
    public ResponseEntity deleteList(@PathVariable("listId") Long listId) {
        listService.deleteList(listId);
        return ResponseEntity.ok().build();
    }
}

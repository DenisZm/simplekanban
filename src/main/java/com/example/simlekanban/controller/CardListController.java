package com.example.simlekanban.controller;

import com.example.simlekanban.entity.Card;
import com.example.simlekanban.entity.CardList;
import com.example.simlekanban.service.CardListService;
import com.example.simlekanban.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lists")
public class CardListController {

    private final CardListService listService;
    private final CardService cardService;

    public CardListController(CardListService listService, CardService cardService) {
        this.listService = listService;
        this.cardService = cardService;
    }

    @GetMapping(path = "/{listId}")
    public ResponseEntity<CardList> getList(@PathVariable("listId") Long listId) {
        return ResponseEntity.ok(listService.getListById(listId));
    }

    @GetMapping(path = "/{listId}/cards")
    public ResponseEntity<List<Card>> getListCards(@PathVariable("listId") Long listId) {
        return ResponseEntity.ok(cardService.getCardsByListId(listId));
    }

    @PostMapping(path = "/{listId}/cards")
    public ResponseEntity<Card> createCard(@PathVariable("listId") Long listId,
                                           @Valid @RequestBody Card card) {
        CardList list = listService.getListById(listId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardService.createCard(list, card));
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

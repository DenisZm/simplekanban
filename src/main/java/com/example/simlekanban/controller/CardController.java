package com.example.simlekanban.controller;

import com.example.simlekanban.dto.CardRequestDto;
import com.example.simlekanban.entity.Card;
import com.example.simlekanban.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping(path = "/{cardId}")
    public ResponseEntity<Card> getCard(@PathVariable("cardId") Long cardId) {
        return ResponseEntity.ok(cardService.getCardById(cardId));
    }

    @PutMapping(path = "/{cardId}")
    public ResponseEntity<Card> updateCard(@PathVariable("cardId") Long cardId,
                                           @Valid @RequestBody CardRequestDto card) {
        return ResponseEntity.ok(cardService.updateCard(cardId, card));
    }
}

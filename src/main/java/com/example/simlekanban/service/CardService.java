package com.example.simlekanban.service;

import com.example.simlekanban.dto.CardUpdate;
import com.example.simlekanban.entity.Card;
import com.example.simlekanban.entity.CardList;
import com.example.simlekanban.exception.EntityNotFoundException;
import com.example.simlekanban.repository.CardRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardListService listService;

    public CardService(CardRepository cardRepository, CardListService listService) {
        this.cardRepository = cardRepository;
        this.listService = listService;
    }

    public List<Card> getCardsByListId(Long listId) {
        return cardRepository.findByCardList_IdOrderByPosition(listId);
    }

    public Card createCard(CardList list, Card card) {
        List<Card> cards = cardRepository.findByCardList_IdOrderByPosition(list.getId());

        if (cards.isEmpty()) {
            card.setPosition((float) 0x10000);
        } else {
            Float max = cards.stream()
                    .map(Card::getPosition)
                    .max(Float::compareTo)
                    .get();
            card.setPosition(max + (float) 0x10000);
        }
        card.setCardList(list);
        return cardRepository.save(card);
    }

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with id " + cardId));
    }

    public Card updateCard(Long cardId, @Valid CardUpdate card) {
        Card cardToUpdate = getCardById(cardId);
        cardToUpdate.setTitle(card.getTitle());
        if (card.getPosition() != null) {
            cardToUpdate.setPosition(card.getPosition());
        }
        if (card.getDescription() != null) {
            cardToUpdate.setDescription(card.getDescription());
        }
        if (card.getListId() != null
                && !cardToUpdate.getCardList().getId().equals(card.getListId())) {
            CardList list = listService.getListById(card.getListId());
            cardToUpdate.setCardList(list);
        }
        return cardRepository.save(cardToUpdate);
    }
}

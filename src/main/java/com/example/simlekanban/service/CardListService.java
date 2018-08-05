package com.example.simlekanban.service;

import com.example.simlekanban.entity.Board;
import com.example.simlekanban.entity.CardList;
import com.example.simlekanban.exception.EntityNotFoundException;
import com.example.simlekanban.repository.CardListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardListService {
    private final CardListRepository listRepository;

    public CardListService(CardListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public List<CardList> getListByBoardId(Long id) {
        return listRepository.findByBoard_IdOrderByPosition(id);
    }

    public CardList createList(Board board, CardList cardList) {
        List<CardList> lists = listRepository.findByBoard_IdOrderByPosition(board.getId());

        if (lists.isEmpty()) {
            cardList.setPosition((float) 0x10000);
        } else {
            Float max = lists.stream()
                    .map(CardList::getPosition)
                    .max(Float::compareTo)
                    .get();
            cardList.setPosition(max + (float) 0x10000);
        }
        cardList.setBoard(board);
        return listRepository.save(cardList);
    }

    public CardList updateList(Long listId, CardList list) {
        CardList listToUpdate = getListById(listId);
        listToUpdate.setName(list.getName());
        if (list.getPosition() != null) {
            listToUpdate.setPosition(list.getPosition());
        }
        return listRepository.save(listToUpdate);
    }

    public void deleteList(Long listId) {
        CardList listToDelete = getListById(listId);
        listRepository.delete(listToDelete);
    }

    public CardList getListById(Long listId) {
        return listRepository.findById(listId)
                .orElseThrow(() -> new EntityNotFoundException("List not found with id " + listId));
    }
}

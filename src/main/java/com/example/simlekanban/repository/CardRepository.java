package com.example.simlekanban.repository;

import com.example.simlekanban.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByCardList_IdOrderByPosition(Long cardListId);
}

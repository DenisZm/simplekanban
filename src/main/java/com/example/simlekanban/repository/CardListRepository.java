package com.example.simlekanban.repository;

import com.example.simlekanban.entity.CardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardListRepository extends JpaRepository<CardList, Long> {
    List<CardList> findByBoard_IdOrderByPosition(Long boardId);
}

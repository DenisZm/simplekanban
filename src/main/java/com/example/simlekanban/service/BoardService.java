package com.example.simlekanban.service;

import com.example.simlekanban.entity.Board;
import com.example.simlekanban.exception.EntityNotFoundException;
import com.example.simlekanban.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Board updateBoard(Long id, Board board) {
        Board boardToUpdate = getBoardById(id);
        boardToUpdate.setName(board.getName());
        return boardRepository.save(boardToUpdate);
    }

    public void deleteBoard(Long id) {
        Board boardToDelete = getBoardById(id);
        boardRepository.delete(boardToDelete);
    }

    public Board getBoardById(Long id) {
        return boardRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id " + id));
    }
}

package com.example.simlekanban.controller;

import com.example.simlekanban.entity.Board;
import com.example.simlekanban.entity.CardList;
import com.example.simlekanban.service.BoardService;
import com.example.simlekanban.service.CardListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardService boardService;
    private final CardListService listService;

    public BoardController(BoardService boardService, CardListService listService) {
        this.boardService = boardService;
        this.listService = listService;
    }

    @GetMapping
    public ResponseEntity<List<Board>> getBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @GetMapping(path = "/{boardId}/lists")
    public ResponseEntity<List<CardList>> getBoardLists(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(listService.getListByBoardId(boardId));
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@Valid @RequestBody Board boardRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(boardService.createBoard(boardRequest));
    }

    @PostMapping(path = "/{boardId}/lists")
    public ResponseEntity<CardList> createList(@PathVariable("boardId") Long boardId,
                                               @Valid @RequestBody CardList cardList) {
        Board board = boardService.getBoardById(boardId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(listService.createList(board, cardList));
    }

    @PutMapping(path = "/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable("boardId") Long boardId,
                                             @Valid @RequestBody Board boardRequest) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, boardRequest));
    }

    @DeleteMapping(path = "/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
}

package com.won.board.service;

import com.won.board.dto.response.BoardResponse;
import com.won.board.entity.Board;
import com.won.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardResponse> findAll() {
        // Board 엔티티 리스트를 가져오고, 각 엔티티를 BoardResponse로 변환
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    public BoardResponse findById(Long id) {
        Board board = boardRepository.findById(id).orElse(null);
        return new BoardResponse(board);
    }
}

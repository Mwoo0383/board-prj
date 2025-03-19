package com.won.board.service;

import com.won.board.dto.response.BoardResponse;
import com.won.board.dto.resquest.BoardRequest;
import com.won.board.entity.Board;
import com.won.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 페이지네이션 메서드
    public Page<BoardResponse> getList(int page) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "id"));

        Page<Board> boardPage = boardRepository.findAll(pageable);
        Page<BoardResponse> responsePage = boardPage.map(BoardResponse::new);

        // 페이지 번호를 1부터 시작하도록 변환
        Page<BoardResponse> adjustedResponsePage = new PageImpl<>(
                responsePage.getContent(),
                pageable,
                boardPage.getTotalElements()
        ) {
            @Override
            public int getNumber() {
                return super.getNumber() + 1; // 페이지 번호에 1을 더해주기
            }
        };
        return adjustedResponsePage;
    }

    // 게시판 조회
    public List<BoardResponse> findAll() {
        // Board 엔티티 리스트를 가져오고, 각 엔티티를 BoardResponse로 변환
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    // 특정 id로 게시판 조회
    public BoardResponse findById(Long id) {
        Board board = boardRepository.findById(id).orElse(null);
        assert board != null;
        return new BoardResponse(board);
    }

    // 게시판 쓰기
    public BoardResponse write(BoardRequest boardRequest) {
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());

        boardRepository.save(board);
        return new BoardResponse(board);
    }

    public BoardResponse update(long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id).orElse(null);

        if (board == null) return null;
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setUpdatedAt(LocalDateTime.now()); // updatedAt 값 설정
        boardRepository.save(board);
        return new BoardResponse(board);
    }

    // 삭제 기능
    public void delete(long id) {
        Board board = boardRepository.findById(id).orElse(null);
        if (board != null) boardRepository.delete(board);
    }
}

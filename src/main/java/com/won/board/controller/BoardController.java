package com.won.board.controller;


import com.won.board.dto.response.BoardResponse;
import com.won.board.dto.resquest.BoardRequest;
import com.won.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Operation(summary = "게시판 목록 조회", description = "게시판 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = BoardResponse.class))),
    })
    @GetMapping()
    public ResponseEntity<List<BoardResponse>> getBoardList() {
        List<BoardResponse> boardList = boardService.findAll();
        return ResponseEntity.ok(boardList);
    }

    @Operation(summary = "특정 게시판 목록 조회", description = "특정 게시판 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = BoardResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable long id) {
        BoardResponse boardResponse = boardService.findById(id);
        return ResponseEntity.ok(boardResponse);
    }

    @Operation(summary = "게시판 작성", description = "게시판 작성을 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = BoardResponse.class))),
    })
    @PostMapping("/write")
    public ResponseEntity<BoardResponse> write(@RequestBody BoardRequest boardRequest) {
        BoardResponse boardResponse = boardService.write(boardRequest);
        return ResponseEntity.ok(boardResponse);
    }
}

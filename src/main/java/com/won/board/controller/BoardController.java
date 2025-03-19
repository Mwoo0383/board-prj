package com.won.board.controller;


import com.won.board.dto.response.BoardResponse;
import com.won.board.dto.resquest.BoardRequest;
import com.won.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시판 목록 조회 (페이징 포함)
    @Operation(summary = "페이징 게시판 목록 조회", description = "게시판 목록을 페이징하여 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = BoardResponse.class))),
    })
    @GetMapping("/list")
    public ResponseEntity<Page<BoardResponse>> getList(
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page) {

        // 요청된 page가 1보다 작으면 1로 고정 (0을 방지)
        if (page < 1) {
            throw new IllegalArgumentException("페이지 번호는 1 이상이어야 합니다.");
        }

        Page<BoardResponse> responsePage = boardService.getList(page);

        return ResponseEntity.ok(responsePage);
    }

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

    @Operation(summary = "특정 게시글 조회", description = "특정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = BoardResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable long id) {
        BoardResponse boardResponse = boardService.findById(id);
        return ResponseEntity.ok(boardResponse);
    }

    @Operation(summary = "게시글 작성", description = "게시글을 작성 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = BoardResponse.class))),
    })
    @PostMapping("/write")
    public ResponseEntity<BoardResponse> write(@RequestBody BoardRequest boardRequest) {
        BoardResponse boardResponse = boardService.write(boardRequest);
        return ResponseEntity.ok(boardResponse);
    }

    @Operation(summary = "게시글 수정", description = "게시글 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = BoardResponse.class))),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BoardResponse> modify(@PathVariable long id, @RequestBody BoardRequest boardRequest) {
        BoardResponse boardResponse = boardService.update(id, boardRequest);
        return ResponseEntity.ok(boardResponse);
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = BoardResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable long id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

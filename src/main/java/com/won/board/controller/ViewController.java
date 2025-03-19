package com.won.board.controller;

import com.won.board.dto.response.BoardResponse;
import com.won.board.dto.resquest.BoardRequest;
import com.won.board.entity.Board;
import com.won.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class ViewController {

    @Autowired
    private BoardService boardService;

    // 게시물 삭제 처리
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);  // 서비스 계층에서 삭제 처리
        return "redirect:/board/list/1"; // 삭제 후 게시글 목록 페이지로 리다이렉트
    }

    // 게시글 수정 페이지
    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable Long id, Model model) {
        // id를 이용해 기존 게시글 정보를 가져옴
        BoardResponse boardResponse = boardService.findById(id);

        // 기존 데이터를 모델에 담아서 수정 폼에 전달
        model.addAttribute("board", boardResponse);

        return "board/modify"; // modify.html (게시글 수정 페이지) 반환
    }

    // 게시글 수정 처리
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id, @ModelAttribute BoardRequest boardRequest) {
        boardService.update(id, boardRequest);
        return "redirect:/board/" + id; // 수정 후 상세 페이지로 이동
    }


    // 게시글 작성 페이지
    @GetMapping("/write")
    public String writeBoardForm(Model model) {
        model.addAttribute("board", new BoardRequest());
        return "board/write";
    }

    // 게시글 작성 처리
    @PostMapping("/write")
    public String writeBoard(@ModelAttribute BoardRequest boardRequest) {
        boardService.write(boardRequest);
        return "redirect:/board/list/1"; // 작성 후 게시글 목록으로 이동
    }

    // 게시글 상세 페이지 (id는 숫자만 허용)
    @GetMapping("/{id}")
    public String boardDetail(@PathVariable("id") Long id, Model model) {
        BoardResponse boardResponse = boardService.findById(id);
        model.addAttribute("board", boardResponse);
        return "board/detail";
    }

    // 게시판 목록 (페이징)
    @GetMapping("/list/{page}")
    public String getPagedBoardList(@PathVariable int page, Model model) {
        if (page < 1) page = 1;

        Page<BoardResponse> boardPage = boardService.getList(page);

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardPage.getTotalPages());

        return "board/list";
    }
}

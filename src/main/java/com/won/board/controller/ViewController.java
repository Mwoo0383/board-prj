package com.won.board.controller;

import com.won.board.dto.response.BoardResponse;
import com.won.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/board")
public class ViewController {

    @Autowired
    private BoardService boardService;

    // 페이징된 게시판 목록을 가져와서 Thymeleaf에 전달
    @GetMapping()
    public String getPagedBoardList(@RequestParam(defaultValue = "1") int page, Model model) {
        if (page < 1) {
            page = 1;
        }

        Page<BoardResponse> boardPage = boardService.getList(page);

        model.addAttribute("boardPage", boardPage);  // 페이징된 게시글 목록
        model.addAttribute("currentPage", page);     // 현재 페이지 번호
        model.addAttribute("totalPages", boardPage.getTotalPages()); // 전체 페이지 수

        return "board/list";  // 'board/list.html' 뷰로 전달
    }

    @GetMapping
    public String getBoardDetail(@RequestParam(defaultValue = "1") int page, Model model) {
        if (page < 1) {
            page = 1;
        }

    }
}

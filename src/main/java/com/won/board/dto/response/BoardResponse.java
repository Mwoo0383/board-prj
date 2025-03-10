package com.won.board.dto.response;

import com.won.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
public class BoardResponse {
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public BoardResponse(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

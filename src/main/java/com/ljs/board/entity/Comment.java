package com.ljs.board.entity;

import com.ljs.board.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        if (dto.getId() != null || dto.getArticleId() != article.getId()) {
            throw new IllegalArgumentException("댓글 생성 실패.");
        }
        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }

    public void patch(CommentDto dto) {
        if (this.id != dto.getId()) {
            throw new IllegalArgumentException("댓글 수정 실패.");
        }
        if (dto.getNickname() != null) {
            this.nickname = dto.getNickname();
        }
        if (dto.getBody() != null) {
            this.body = dto.getBody();
        }
    }
}
package com.ljs.board.repository;

import com.ljs.board.entity.Article;
import com.ljs.board.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleIdTest() {
        /* Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            // given
            Long articleId = 4L;
            Article article = new Article(4L, "article 4", "당신의 인생 영화는?");
            Comment comment1 = new Comment(1L, article, "Park", "굿 윌 헌팅");
            Comment comment2 = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment comment3 = new Comment(3L, article, "Choi", "쇼생크 탈출");
            List<Comment> expected = Arrays.asList(comment1, comment2, comment3);

            // when
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // then
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 조회한다.");
        }
        /* Case 2: 1번 게시글의 모든 댓글 조회 */
        {
            // given
            Long articleId = 1L;

            // when
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // then
            assertEquals(0, comments.size(), "1번 글은 댓글이 없다.");
        }
        /* Case 3: 9번 게시글의 모든 댓글 조회 */
        {
            // given
            Long articleId = 9L;

            // when
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 4. 비교 및 검증
            assertEquals(0, comments.size(), "9번 글 자체가 없으므로 댓글은 비어 있어야 한다.");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        // given
        String nickname = "Choi";

        // when
        List<Comment> comments = commentRepository.findByNickname(nickname);

        // then
        assertEquals(3, comments.size(), "Choi의 모든 댓글을 조회한다.");
    }
}
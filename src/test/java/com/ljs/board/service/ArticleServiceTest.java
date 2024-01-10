package com.ljs.board.service;

import com.ljs.board.dto.ArticleForm;
import com.ljs.board.entity.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // given
        Article article1 = new Article(1l, "article 1", "content ...");
        Article article2 = new Article(2l, "article 2", "content ...");
        Article article3 = new Article(3l, "article 3", "content ...");
        Article article4 = new Article(4L, "article 4", "당신의 인생 영화는?");
        Article article5 = new Article(5L, "article 5", "당신의 소울 푸드는?");
        Article article6 = new Article(6L, "article 6", "당신의 취미는?");
        List<Article> expected = Arrays.asList(article1, article2, article3, article4, article5, article6);

        // when
        List<Article> articleList = articleService.index();

        // then
        assertEquals(expected.toString(), articleList.toString());
    }

    @Test
    @DisplayName("게시글이 존재하지 않는 경우 조회 실패")
    void show_fail() {
        // given
        Long id = -1L;
        Article expected = null;

        // when
        Article article = articleService.show(id);

        // then
        assertThat(article).isNull();
    }

    @Test
    @DisplayName("게시글이 존재하는 경우 조회 성공")
    void show_success() {
        // given
        Long id = 1L;
        Article expected = new Article(id, "article 1", "content ...");

        // when
        Article article = articleService.show(id);

        // then
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("id가 포함되어 있는 게시글 생성 실패")
    @Transactional
    void create_fail() {
        // given
        String title = "article 4";
        String content = "content ...";
        ArticleForm dto = new ArticleForm(4L, title, content);

        // when
        Article article = articleService.create(dto);

        // then
        assertThat(article).isNull();
    }

    @Test
    @DisplayName("제목과 내용만 있는 게시글 생성 성공")
    @Transactional
    void create_success() {
        // given
        String title = "article 7";
        String content = "content ...";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(7L, title, content);

        // when
        Article article = articleService.create(dto);

        // then
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("게시글이 존재하지 않는 경우 수정 실패")
    @Transactional
    void update_fail() {
        // given
        Long id = 9L;
        String title = "updated article";
        String content = "updated content ...";
        ArticleForm dto = new ArticleForm(id, title, content);

        // when
        Article expected = articleService.update(id, dto);

        // then
        assertEquals(null, expected);
    }

    @Test
    @DisplayName("존재하는 게시글 수정 성공")
    @Transactional
    void update_success() {
        // given
        Long id = 1L;
        String title = "updated article";
        String content = "updated content ...";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);

        // when
        Article article = articleService.update(id, dto);

        // then
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("게시글이 존재하지 않는 경우 삭제 실패")
    @Transactional
    void delete_fail() {
        // given
        Long id = 9L;

        // when
        Article expected = articleService.delete(id);

        // then
        assertEquals(null, expected);
    }

    @Test
    @DisplayName("존재하는 게시글 삭제 성공")
    @Transactional
    void delete_success() {
        // given
        Long id = 1L;
        Article expected = new Article(id, "article 1", "content ...");

        // when
        Article article = articleService.delete(id);

        // then
        assertEquals(expected.toString(), article.toString());
    }
}
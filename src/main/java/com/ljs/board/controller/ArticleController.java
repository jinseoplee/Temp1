package com.ljs.board.controller;

import com.ljs.board.dto.ArticleForm;
import com.ljs.board.dto.CommentDto;
import com.ljs.board.entity.Article;
import com.ljs.board.repository.ArticleRepository;
import com.ljs.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/create")
    public String createArticle(ArticleForm form) {
        Article article = form.toEntity();
        Article saved = articleRepository.save(article);
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
        model.addAttribute("article", article);
        model.addAttribute("commentDtos", commentDtos);
        return "articles/show";
    }

    @GetMapping
    public String index(Model model) {
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList", articleList);
        return "articles/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", article);
        return "articles/edit";
    }

    @PostMapping("/update")
    public String update(ArticleForm form) {
        Article article = form.toEntity();
        Article target = articleRepository.findById(article.getId()).orElse(null);
        if (target != null) {
            articleRepository.save(article);
        }
        return "redirect:/articles/" + article.getId();
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article != null) {
            articleRepository.delete(article);
            redirectAttributes.addFlashAttribute("msg", "Delete completed");
        }
        return "redirect:/articles";
    }
}
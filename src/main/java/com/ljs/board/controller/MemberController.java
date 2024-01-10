package com.ljs.board.controller;

import com.ljs.board.dto.MemberForm;
import com.ljs.board.entity.Member;
import com.ljs.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signUpPage() {
        return "members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm form) {
        Member member = form.toEntity();
        Member saved = memberRepository.save(member);
        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {
        List<Member> memberList = memberRepository.findAll();
        model.addAttribute("memberList", memberList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form) {
        Member member = form.toEntity();
        Member target = memberRepository.findById(member.getId()).orElse(null);
        if (target != null) {
            memberRepository.save(member);
        }
        return "redirect:/members/" + member.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            memberRepository.delete(member);
            redirectAttributes.addFlashAttribute("msg", "Delete completed");
        }
        return "redirect:/members";
    }
}

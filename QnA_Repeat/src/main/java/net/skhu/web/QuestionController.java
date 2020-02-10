package net.skhu.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.domain.Question;
import net.skhu.domain.QuestionRepository;
import net.skhu.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		Question question = questionRepository.findById(id).get();
		model.addAttribute("question", question);

		return "/qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		
		//글쓴이와 삭제대상이 동일한지?
		if(!question.isSameWriter(loginUser)) {
			return "/users/loginForm";//로긴폼으루
		}
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		//글쓴이와 삭제대상이 동일한지?
		if(!question.isSameWriter(loginUser)) {
			return "/users/loginForm";//로긴폼으루
		}
		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		
		//글쓴이와 삭제대상이 동일한지?
		if(!question.isSameWriter(loginUser)) {
			return "/users/loginForm";//로긴폼으루
		}
		
		questionRepository.deleteById(id);
		return "redirect:/";
	}
}
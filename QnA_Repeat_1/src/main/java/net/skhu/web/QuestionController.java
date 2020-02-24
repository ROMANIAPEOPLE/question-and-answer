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

	@GetMapping("/form") // index(메인)에서 [질문하기] 버튼을 눌렀을때 요청되는 컨트롤러.
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm"; // 로그인 여부 확인
		}
		return "/qna/form";
	}

	@PostMapping("") // qna form에서 제목과 내용을 채우고 게시글 등록하는 컨트롤러.
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User sessionUser = HttpSessionUtils.getUserSession(session);
		// 로그인 정보

		Question newQuestion = new Question(sessionUser, title, contents);

		questionRepository.save(newQuestion);

		return "redirect:/";
	}

	@GetMapping("/{id}") // 제목을 클릭하면 나오는 글 내용
	public String show(@PathVariable Long id, Model model) {
		Question question = questionRepository.findById(id).get();
		model.addAttribute("question", question);
		return "/qna/show";
	}

	@GetMapping("/{id}/form") // 글 [수정]을 눌렀을 때 요청되는 컨트롤러
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		
		User loginUser = HttpSessionUtils.getUserSession(session);
		Question question = questionRepository.findById(id).get();
		if(!question.isSameWriter(loginUser)) {
			session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
			return "/users/loginForm";
		}
		
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	@PostMapping("/{id}") // updateForm에서 내용 다 채우고 [수정하기] 버튼 눌렀을 때 사용되는 컨트롤러.
	public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User loginUser = HttpSessionUtils.getUserSession(session);
		Question question = questionRepository.findById(id).get();
		if(!question.isSameWriter(loginUser)) {
			session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
			return "/users/loginForm";
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
		
		User loginUser = HttpSessionUtils.getUserSession(session);
		Question question = questionRepository.findById(id).get();
		if(!question.isSameWriter(loginUser)) {
			session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
			return "/users/loginForm";
		}
		
		questionRepository.deleteById(id);
		return "redirect:/";
	}
	
	
	
	
	
}
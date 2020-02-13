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

	private boolean hasPermission(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		return true;
	}

	@GetMapping("/form")   // 질문하기 버튼을 눌렀을때 요청되는 컨트롤러.
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm"; //로그인되지 않은 상태라면 로그인폼으로 이동한다.
		}
		return "/qna/form"; //로그인되어있다면 질문글을 입력하는 form으로 이동한다.
	}

	@PostMapping("") //폼에 제목과 내용을 입력하고 질문글 등록을 누를때 사용되는 컨트롤러.
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/";
	}

	@GetMapping("/{id}") //질문글 눌렀을때 요청되는 컨트롤러로 questions/id이다. id값이 qna/show에 모델로 전달된다.
	public String show(@PathVariable Long id, Model model) {
		Question question = questionRepository.findById(id).get();
		model.addAttribute("question", question);

		return "/qna/show";
	}

	@GetMapping("/{id}/form") //수정 버튼을 눌렀을 때 사용되는 컨트롤러. 유효성검사
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

		try {
			Question question = questionRepository.findById(id).get(); // 질문글의 ID를 가져옴
			model.addAttribute("question", question); //model에 추가
			hasPermission(session, question); // seesion(로그인정보)와 질문글(id) 유효성 검사
			return "/qna/updateForm";  //유효하다면 여기로 리턴
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login"; //유효하지 않다면 에러메시지를 보내고 여기로 리턴
		}
	}

	@PostMapping("/{id}") // 질문글 수정 컨트롤러
	public String update(@PathVariable Long id, String title, String contents, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			question.update(title, contents);
			questionRepository.save(question);
			return String.format("redirect:/questions/%d", id);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}

	@PostMapping("/{id}/delete") //질문글 삭제 컨트롤러
	public String delete(@PathVariable Long id, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			questionRepository.deleteById(id);
			return "redirect:/";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}

	}
}
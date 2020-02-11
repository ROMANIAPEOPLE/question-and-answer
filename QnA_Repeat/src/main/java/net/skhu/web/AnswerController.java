package net.skhu.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.domain.Answer;
import net.skhu.domain.AnswerRepository;
import net.skhu.domain.Question;
import net.skhu.domain.QuestionRepository;
import net.skhu.domain.User;

@Controller
@RequestMapping("questions/{questionId}/answers")
public class AnswerController {
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User loginUser =HttpSessionUtils.getUserFromSession(session);
		//로그인 유저 얻어옴
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(question, loginUser, contents);
		answerRepository.save(answer);
		return String.format("redirect:/questions/%d", questionId);
		
	}

}

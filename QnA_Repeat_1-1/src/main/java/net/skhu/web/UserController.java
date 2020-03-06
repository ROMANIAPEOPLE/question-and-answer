package net.skhu.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.domain.User;
import net.skhu.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserRepository userRepository;

	// [회원가입] 버튼을 눌렀을때, 회원가입 form 으로 이동한다.
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	// 회원가입 form에서 가입완료를 눌렀을때 userRepository에 그 회원의 정보(user)를 저장한다.
	@PostMapping("")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}

	// 회원 목록 list
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	// list에서 [수정] 버튼을 눌렀을 때 이동하는 updateForm
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("본인정보만.");
		}
		model.addAttribute("user", userRepository.findById(id).get());
		return "/user/updateForm";
	}

	//[수정하기] 버튼을 눌렀을때 사용되는 컨트롤러
	@PostMapping("/{id}")
	public String update(User updateUser, @PathVariable Long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("본인정보만.");
		}

		User user = userRepository.findById(id).get();
		user.updateUser(updateUser);
		userRepository.save(user);
		return "redirect:/users";
		
		
		
		
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);

		if (user == null) {
			return "redirect:/users/loginForm";
		}

		if (!user.mathchPassword(password)) {
			return "redirect:/users/loginForm";
		}

		session.setAttribute(HttpSessionUtils.USER_SEESION_KEY, user);

		return "redirect:/";
	}
	
	@GetMapping("/logout") 
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SEESION_KEY);
		
		return "redirect:/";
		
	}

}

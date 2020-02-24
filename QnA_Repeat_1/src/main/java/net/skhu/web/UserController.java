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
	private UserRepository userRepository;

	@GetMapping("/form")  // [회원가입] 을 눌렀을때, /users/form 을 요청받고   user/form.html 을 보여준다.
	public String form() {
		return "/user/form";
	}

	@PostMapping("")
	public String create(User user) {  //[회원가입 form]에서 정보들을 user에 채운 후, userRepository에 저장. 
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")  // /users를 get방식으로 전달받으면 list를 보여준다.
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/{id}/form") // list에서 [수정] 버튼을 눌렀을 때 요청되는 users/id/form 
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";  //현재 로그인 상태인지 확인, 로그인이 안되어있다면 loginForm으로 이동
		}

		User sessionedUser = HttpSessionUtils.getUserSession(session); //현재 로그인 되어있는 정보를 sessionedUser에 저장.
		if (!sessionedUser.matchId(id)) {	//이 메소드에서 id값을 받아온 이유는, 로그인되어있는 session과 [수정]하려는 session의 일치여부 확인 위해서..
			throw new IllegalStateException("본인정보만.");
		}

		User user = userRepository.findById(id).get(); // userRepositroy에 id로 user정보를 찾아서 모델에 보여준다.
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, User updateUser, HttpSession session) { //[수정하기] 버튼을 눌렀을때 요청되는 컨트롤러
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}

		User sessionedUser = HttpSessionUtils.getUserSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("본인정보만.");
			//회원정보에 있는 id와 현재 로그인된 id값을 비교
		}

		User user = userRepository.findById(id).get();
		user.update(updateUser);
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

		if (!user.mathPassword(password)) {
			return "redirect:/users/loginForm";
		}

		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
}

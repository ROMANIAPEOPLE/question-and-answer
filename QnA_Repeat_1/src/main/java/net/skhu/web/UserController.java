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

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@PostMapping("")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model) {
		User user= userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PostMapping("/{id}")
	//수정하기 버튼을 눌렀을 때 요청되는 url
	public String update(@PathVariable Long id, User updateUser) {
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
	
		if(user == null) {
			return "redirect:/users/loginForm";
		}
		
		if(!password.equals(user.getPassword())) {
			return "redirect:/users/loginForm";
		}
		
		session.setAttribute("user", user);
		
		return "redirect:/";
	}
	
	
	
	

}

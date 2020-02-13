package net.skhu.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.domain.User;

@Controller
@RequestMapping("/users")
public class UserController {

	private List<User> users = new ArrayList<User>();
	
	@PostMapping("create") 
	public String create(User user) {
		users.add(user);
		return "redirect:/list";
	}
	
	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("users",users);
		return "list";
	}

}

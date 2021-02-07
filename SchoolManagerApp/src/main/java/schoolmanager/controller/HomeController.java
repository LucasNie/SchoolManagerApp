package schoolmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import schoolmanager.entity.Student;
import schoolmanager.service.StudentService;

@Controller
public class HomeController {

	@Autowired
	StudentService studentService;
	
	@GetMapping("/")
	public String showHome(Model theModel) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Student theStudent = studentService.findByUsername(currentPrincipalName);
		theModel.addAttribute("user", theStudent);
		return "home";
	}
	
	@GetMapping("/showLoginPage")
	public String showLoginPage() {
		return "plain-login";
	}
	
	@RequestMapping("/access-denied")
	public String showAccessDenied() {
		return "access-denied";
	}
	
}

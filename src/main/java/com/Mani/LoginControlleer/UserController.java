package com.Mani.LoginControlleer;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Mani.LoginEntity.User;
import com.Mani.LoginService.UserService;

import jakarta.servlet.http.HttpSession;

@Controller

public class UserController {

	@Autowired
	UserService userser;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("home");
		return "home";
	}

	@GetMapping("/registerpage")
	public String getregister(Model model) {
		model.addAttribute("registeration", new User());
		return "register";
	}

	@GetMapping("/loginpage")
	public String getlogin(Model model) {
		model.addAttribute("loggedin", new User());
		return "login";
	}

	@PostMapping("/register")
	public String newregister(@ModelAttribute User user, Model model) {

		System.out.println("Attempting to register user: " + user);
		if (isUserAlreadyRegistered(user.getEmail())) {
			model.addAttribute("registrationError", "Sorry Email Already Exist please login!!!");
			model.addAttribute("showModel", true);
		} else {
			User registeredUser = userser.register(user.getName(), user.getEmail(), user.getPassword());

			if (registeredUser != null) {
				System.out.println("User successfully registered");
				return "login";
			} else {
				model.addAttribute("registrationError", "Error during registration. Please try again.");
				return "register";
			}
		}
		return "register";
	}

	private boolean isUserAlreadyRegistered(String email) {
		boolean isRegistered = userser.isUserAlreadyRegistered(email);
		System.out.println("Checking if user is already registered: " + email + " - Result: " + isRegistered);
		return isRegistered;

	}

	@PostMapping("/login")
	public String newlogin(@ModelAttribute User user, Model model) {

		try {
			User authenticatedUser = userser.auth(user.getEmail(), user.getPassword());
			model.addAttribute("sucess", authenticatedUser);
			
			return "welcome";
		} catch (NoSuchElementException e) {

			model.addAttribute("error", "Invalid email or password please try Again!!!");
			model.addAttribute("showmodal", true);

			return "login";
		}
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		
		return "confirmLogout";
	}

	@PostMapping("/logout1")
	public String logout1(HttpSession session,Model model, 
	                     @RequestParam(name = "confirm", defaultValue = "false") boolean confirm) {
		System.out.println("if working");
	    if (confirm) {
	        session.invalidate();
	        System.out.println("if working");
	       
	        model.addAttribute("message", "You have been successfully logged out.");
	        model.addAttribute("show", true);
	        return "home";
	    } else {
	        model.addAttribute("message", "Logout canceled.");
	        
	        return "welcome";
	    }
	}

}

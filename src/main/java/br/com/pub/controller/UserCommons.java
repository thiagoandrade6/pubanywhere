package br.com.pub.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import br.com.pub.domain.Users;
import br.com.pub.service.UserService;

@Controller
public abstract class UserCommons {
	
	@Autowired private UserService userService;
	
	public Users getUserSession(HttpSession session) {
		Users loggedUser = null;
		if (session.getAttribute("loggedUser") == null) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			loggedUser = userService.findUserByUsername(user.getUsername());
			session.setAttribute("loggedUser", loggedUser);
		} else {
			loggedUser = (Users) session.getAttribute("loggedUser");
		}
		return loggedUser;
	}
}
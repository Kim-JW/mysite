package com.poscoict.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poscoict.mysite.security.Auth;

// @Auth
@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping("")
	public String main() {
		return "admin/main";
	}
	
	@ResponseBody
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@ResponseBody
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}

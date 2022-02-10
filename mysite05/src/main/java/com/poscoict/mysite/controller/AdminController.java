package com.poscoict.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.service.FileUploadService;
import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.SiteVo;

// @Auth
@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping("")
	public String main(Model model) {
		
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		
		return "admin/main";
	}
	
	@RequestMapping("/main/update")
	public String updateMain(@RequestParam(value = "upload-file") MultipartFile multipartfile, SiteVo siteVo) {
		
		if(multipartfile.isEmpty()) {
			System.out.println("file is empty");
		}
		
		String url = fileUploadService.restore(multipartfile);
		
		/*
		 * hidden 으로 default 사진을 숨겨 두고
		 * 
		 *  if(url != null) {
		 *  	siteVo.setProfile 을 세팅한다.
		 *  }
		 * 
		 */
		
		siteVo.setProfile(url);
		
		servletContext.setAttribute("siteVo", siteVo);
		
		siteService.update(siteVo);
		return "redirect:/admin";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}

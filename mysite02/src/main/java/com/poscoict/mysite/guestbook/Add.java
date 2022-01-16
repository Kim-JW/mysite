package com.poscoict.mysite.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestBookDao;
import com.poscoict.mysite.vo.GuestBookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class Add implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
	 	String message = request.getParameter("message");
	 	
	 	GuestBookVo vo = new GuestBookVo();
	 	vo.setName(name);
		vo.setPassword(password);
		vo.setMessage(message);
		
		boolean result = new GuestBookDao().insert(vo);
		String url = request.getContextPath() +"/guestbook";
		//MvcUtil.forward(url, request, response);
		//MvcUtil.forward("guestbook/list", request, response);
		MvcUtil.redirect(url, request, response);

	}

}

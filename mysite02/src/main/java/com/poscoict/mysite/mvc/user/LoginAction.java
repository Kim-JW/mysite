 package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.UserDao;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class LoginAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo authUser = new UserDao().findByEmailAndPassword(email, password);
		
		if(authUser == null) {
			/* 이메일 또는 비밀 번호가 틀림 */
			request.setAttribute("result", "fail");
			request.setAttribute("email", email);
			MvcUtil.forward("user/loginform", request, response);
			return;
		}
		
		// 인증 처리(Session 처리)
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		session.setAttribute("UserNo", authUser.getNo());
		
		MvcUtil.redirect(request.getContextPath(), request, response);
	}

}

package com.poscoict.mysite.guestbook;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestBookDao;
import com.poscoict.mysite.vo.GuestBookVo;
import com.poscoict.web.mvc.Action;

public class DeleteForm implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long no = null;

		if (request.getParameter("no") != null)
			no = Long.parseLong(request.getParameter("no"));
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
		request.setAttribute("no", no);
		rd.forward(request, response);
	}

}

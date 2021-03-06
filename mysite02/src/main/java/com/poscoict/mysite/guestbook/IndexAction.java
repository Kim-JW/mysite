package com.poscoict.mysite.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestBookDao;
import com.poscoict.mysite.vo.GuestBookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class IndexAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestBookDao dao = new GuestBookDao();
		List<GuestBookVo> list = dao.findAll();

		request.setAttribute("list", list);
		
		MvcUtil.forward("guestbook/list", request, response);

	}

}

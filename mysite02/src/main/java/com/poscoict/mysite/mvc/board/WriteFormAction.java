package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class WriteFormAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("g_no") != null) {
			int groupNo = Integer.parseInt(request.getParameter("g_no"));
			int orderNo = Integer.parseInt(request.getParameter("o_no"));
			Long no = Long.parseLong(request.getParameter("no"));
			
			request.setAttribute("groupNo", groupNo);
			request.setAttribute("orderNo", orderNo);
			request.setAttribute("no", no);
		}
		
		MvcUtil.forward("board/writeform", request, response);
	}

}

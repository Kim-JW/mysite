package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ModifyAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long no = null;
		if(request.getParameter("no") != null) {
			no = Long.parseLong(request.getParameter("no"));
		}
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		boolean result = new BoardDao().update(no, title, contents);
		
		
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}

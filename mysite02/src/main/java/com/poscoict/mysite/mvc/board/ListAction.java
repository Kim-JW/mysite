package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int splitNum = 5;
		int currentPage = 1;
		
		int pageCount = 10;
		
		int nextPage = -1;
		int startPage = 3;
		int prevPage = 2;
		
		BoardDao dao = new BoardDao();
		
		HttpSession session = request.getSession();
		
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		session.setAttribute("pageNum", dao.pageNum(splitNum));
		session.setAttribute("currentPage", currentPage);
		
		
		
		List<BoardVo> list = new BoardDao().findAll((currentPage-1)*splitNum, splitNum);
		
		request.setAttribute("list", list);
		
		MvcUtil.forward("board/list", request, response);

	}

}

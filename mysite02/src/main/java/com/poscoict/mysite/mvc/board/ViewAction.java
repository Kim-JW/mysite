package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String No_str = request.getParameter("no");
		
		Long no = Long.parseLong(No_str);
		BoardVo vo = new BoardDao().findBoardByNo(no);
		
		request.setAttribute("list", vo);
		
		Cookie[] cookies = request.getCookies();
		Cookie viewCookie = null;
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (No_str.equals(cookie.getName())) {
					viewCookie = cookie;
					break; 
				}
			}
		}

		// 쿠키 쓰기(굽기)
		if (viewCookie == null) {
			Cookie cookie = new Cookie(No_str, String.valueOf((vo.getHit() + 1)));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(60);
			response.addCookie(cookie);
			new BoardDao().IncreaseCnt(vo);

		} else {

		}
		
		MvcUtil.forward("/board/view", request, response);

	}

}

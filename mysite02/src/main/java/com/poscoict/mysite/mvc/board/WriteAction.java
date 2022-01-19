package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Long no = null;
		BoardVo parentVo = null;
		BoardDao dao = new BoardDao();
		
		if(request.getParameter("no") != null) {
			no = Long.parseLong(request.getParameter("no"));
			parentVo = dao.findBoardByNo(no);
		}
		
		BoardVo vo = new BoardVo();
		
		HttpSession session = request.getSession();
		UserVo userVo= (UserVo) session.getAttribute("authUser");
		
		Long UserNo = userVo.getNo();
		
		// 새글 쓰기 영역 
		if (request.getParameter("g_no") == null) {
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setHit(0);
			vo.setGroupNo(0);
			vo.setOrderNo(1);
			vo.setDepth(1);
			vo.setUserNo(UserNo);
			
			boolean result = dao.insert(vo);
			
		} else { // groupNo 를 받아서 업데이트 후 새글 넣는 답글 영
			int groupNo = Integer.parseInt(request.getParameter("g_no"));
			int orderNo = Integer.parseInt(request.getParameter("o_no"));
			
			vo.setGroupNo(groupNo);
			vo.setOrderNo(orderNo);
			
			boolean result = dao.replyUpdate(vo);
			
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setHit(0);
			vo.setGroupNo(groupNo);
			vo.setOrderNo(orderNo+1);
			vo.setDepth(parentVo.getDepth()+1);
			vo.setUserNo(UserNo);
			
			dao.replyInsert(vo);
			
			
		}
		
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}

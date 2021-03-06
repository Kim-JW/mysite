package com.poscoict.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. handler 종류 확인
		if (handler instanceof HandlerMethod == false) {
			return true;
		}

		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 3. Handler Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		if (auth == null) {
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
		}
		
		// 4. Handler Method @Auth가 없다면 Type에 있는지 확인(과제)
		
		// 5. type과 method에 @Auth 가 적용이 안되어 있는 경우
		// type 에 붙어 있는지 찾아야 한다.
		
		if (auth == null) {
			return true;
		}

		// 5. @Auth가 적용이 되어 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		/*
		 * 7. 권한(Authorization) 체크를 위해서 @Auth role 가져오기.("USER","ADMIN")
		 * 
		 * String role = auth.role();
		 * 
		 * 8. @Auth의 role이 "USER"인 경우, authUser의 role은 상관이 없다.
		 * 
		 * if("USER".equals(role)) {
		 * 		return true;
		 * }
		 * 
		 * */

		String role = authUser.getRole();
		if (!"ADMIN".equals(role)) {
			response.sendRedirect(request.getContextPath());
			return false;
		}

		// 6. 인증이 확인되어 처리를 넘겨준다. ->Controller의 handler(method) 실행
		
		
		
		
		return true;
	}
}

package kh.study.shop.intercepter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kh.study.shop.admin.service.AdminService;

public class CategoryIntercepter implements HandlerInterceptor {
	@Resource(name = "adminService")
	private AdminService adminService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//사용 중인 카테고리 목록 조회
		//modelAndView.addObject("categoryListInUse", adminService.selectCategoryListInUse());
		request.setAttribute("categoryListInUse", adminService.selectCategoryListInUse());
	}
	
	
	
}

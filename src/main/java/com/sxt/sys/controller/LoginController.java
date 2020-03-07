package com.sxt.sys.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sxt.sys.common.ActiverUser;
import com.sxt.sys.common.ResultObj;
import com.sxt.sys.common.WebUtils;

/**
 * <p>
 *  登陆前端控制器
 * </p>
 *
 *
 */
@RestController
@RequestMapping("login")
public class LoginController {
	
	@RequestMapping("login")
	public ResultObj login(String loginname,String pwd) {
		Subject subject = SecurityUtils.getSubject();
//		获取subject来对比
		AuthenticationToken token=new UsernamePasswordToken(loginname, pwd);
//		生成进行通行的token
		try {
			subject.login(token);
//			若通过
			ActiverUser activerUser=(ActiverUser) subject.getPrincipal();
//			获取activeuser加入到session
			WebUtils.getSession().setAttribute("user", activerUser.getUser());
			return ResultObj.LOGIN_SUCCESS;
		} catch (AuthenticationException e) {
			e.printStackTrace();

			return ResultObj.LOGIN_ERROR_PASS;
		}
	}
}


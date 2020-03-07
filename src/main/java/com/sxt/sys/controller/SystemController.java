package com.sxt.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sys")
public class SystemController {
	
	/**
	 * 跳转到登陆页面
	 */
	@RequestMapping("toLogin")
	public String toLogin() {
		return "system/index/login";
	}
	
	/**
	 * 跳转到首页
	 */
	@RequestMapping("index")
	public String index() {
//		System.out.println("hello");
//		for (int i = 0; i < 10; i++) {
//			System.out.println("nmsl");
//		}

		return "system/index/index";
	}

	@RequestMapping("toDeskManager")
	public String toDeskManager(){
		return "system/index/deskManager";
	}

	@RequestMapping("toLoginfoManager")
	public String toLoginfoManager(){
		return "system/loginfo/loginfoManager";
	}
	@RequestMapping("toNoticeManager")
	public String toNoticeManager(){
		return "system/notice/noticeManager";
	}

	@RequestMapping("toDeptManager")

	public String toDeptManager(){
		return "system/dept/deptManager";
	}

	@RequestMapping("toDeptLeft")

	public String toDeptLeft(){
		return "system/dept/deptLeft";
	}

	@RequestMapping("toDeptRight")

	public String toDeptRight(){
		return "system/dept/deptRight";
	}
	/**
	 * 跳转到菜单管理
	 *
	 */
	@RequestMapping("toMenuManager")
	public String toMenuManager() {
		return "system/menu/menuManager";
	}

	/**
	 * 跳转到菜单管理-left
	 *
	 */
	@RequestMapping("toMenuLeft")
	public String toMenuLeft() {
		return "system/menu/menuLeft";
	}


	/**
	 * 跳转到菜单管理--right
	 *
	 */
	@RequestMapping("toMenuRight")
	public String toMenuRight() {
		return "system/menu/menuRight";
	}

	@RequestMapping("toPermissionManager")
	public String toPermissionManager() {
		return "system/permission/permissionManager";
	}

	/**
	 * 跳转到权限管理-left
	 *
	 */
	@RequestMapping("toPermissionLeft")
	public String toPermissionLeft() {
		return "system/permission/permissionLeft";
	}


	/**
	 * 跳转到权限管理--right
	 *
	 */
	@RequestMapping("toPermissionRight")
	public String toPermissionRight() {
		return "system/permission/permissionRight";
	}
	@RequestMapping("toRoleManager")
	public String toRoleManager() {
		return "system/role/roleManager";
	}

	@RequestMapping("toUserManager")
	public String toUserManager() {
		return "system/user/userManager";
	}

	@RequestMapping("loginOut")
	public String loginOut(){
		return "system/index/login";
	}
}


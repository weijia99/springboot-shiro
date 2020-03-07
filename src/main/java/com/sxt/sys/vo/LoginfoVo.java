package com.sxt.sys.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.sxt.sys.domain.Loginfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
//vo层是传入时的实体，所需要拥有的特性，spring自动封装

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginfoVo extends Loginfo{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Integer page=1;
	private Integer limit=10;
	
	private Integer[] ids;//接收多个ID
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
}

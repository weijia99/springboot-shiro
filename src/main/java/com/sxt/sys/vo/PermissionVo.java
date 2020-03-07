package com.sxt.sys.vo;

import com.sxt.sys.domain.Permission;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PermissionVo extends Permission{/**
	 *
 *
 * 继承了permission就是有permission的特性，而且是序列化后的结果
	 */
	private static final long serialVersionUID = 1L;

	private Integer page=1;
	private Integer limit=10;
//	好用来分页

	
	
	
}

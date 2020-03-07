package com.sxt.sys.service;

import com.sxt.sys.domain.Role;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 */
public interface RoleService extends IService<Role> {

	/**
	 * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
	 * @param roleId
	 * @return
	 */
	List<Integer> queryRolePermissionIdsByRid(Integer roleId);

	/**
	 * 保存角色和菜单权限之间的关系
	 * @param roleId
	 * @param ids
	 */
	void saveRolePermission(Integer roleId, Integer[] ids);

    List<Integer> queryUserRoleIdsByUid( Integer id);
}

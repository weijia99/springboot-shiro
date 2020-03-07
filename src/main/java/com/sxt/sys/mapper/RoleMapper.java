package com.sxt.sys.mapper;

import com.sxt.sys.domain.Role;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 根据角色ID删除sys_role_permission
	 * @param id
	 */
	void deleteRolePermissionByRid(Serializable id);

	/**
	 * 根据角色ID删除sys_role_user
	 * @param id
	 */
	void deleteRoleUserByRid(Serializable id);

	/**
	 * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
	 * @param roleId
	 * @return
	 */
	List<Integer> queryRolePermissionIdsByRid(Integer roleId);

	/**
	 * 保存角色和菜单权限之间的关系
	 * @param rid
	 * @param pid
	 */
	void saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);

    void deleteRoleUserByUid(@Param("id") Serializable id);

    List<Integer> queryUserRoleIdsByUid(@Param("id") Integer id);

    void insertUserRole(@Param("uid") Integer uid, @Param("rid") Integer rid);
}

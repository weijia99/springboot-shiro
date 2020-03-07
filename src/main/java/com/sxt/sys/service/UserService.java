package com.sxt.sys.service;

import com.sxt.sys.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *

 */
public interface UserService extends IService<User> {

    void saveUserRole(Integer uid, Integer[] ids);
}

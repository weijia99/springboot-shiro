package com.sxt.sys.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxt.sys.common.Constast;
import com.sxt.sys.common.DataGridView;
import com.sxt.sys.common.PinyinUtils;
import com.sxt.sys.common.ResultObj;
import com.sxt.sys.domain.Dept;
import com.sxt.sys.domain.Role;
import com.sxt.sys.domain.User;
import com.sxt.sys.service.DeptService;
import com.sxt.sys.service.RoleService;
import com.sxt.sys.service.UserService;
import com.sxt.sys.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;



    /**
     * 用户全查询
     */

    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo userVo){
        IPage<User> page=new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getName()), "name", userVo.getName()).or().eq(StringUtils.isNotBlank(userVo.getName()), "name", userVo.getName());;
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()), "address", userVo.getAddress());
        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);//查询系统用户

        queryWrapper.eq(userVo.getDeptid()!=null, "deptid",userVo.getDeptid());
        userService.page(page, queryWrapper);
        List<User> list = page.getRecords();
//        查询dept表，给user里添加属性
        for (User user:
             list) {
            Integer deptid = user.getDeptid();
            if (deptid!=null){
                Dept dept = deptService.getById(deptid);
                user.setDeptname(dept.getTitle());
            }
            Integer mgr = user.getMgr();
            if(mgr!=null) {
//                获取直系领导人的姓名
                User one = this.userService.getById(mgr);
                String name=one.getName();
                user.setLeadername(name);

            }

        }
        return new DataGridView(page.getTotal(), list);

    }

    /**
     * 加载最大的排序码
     * @param
     * @return
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String,Object> loadUserMaxOrderNum(){
        Map<String, Object> map=new HashMap<>();

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<User> page=new Page<>(1, 1);
        List<User> list = this.userService.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getOrdernum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }


//    根据部门号查id
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptid) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(deptid!=null, "deptid", deptid);
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
//        可以用
        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);
//        普通人士
        List<User> list = this.userService.list(queryWrapper);
        return new DataGridView(list);
    }

    @RequestMapping("changeChineseToPinyin")
    public Map<String,Object> changeChineseToPinyin(String username){



        Map<String,Object> map=new HashMap<>();
        if (null!=username){
           map.put("value", PinyinUtils.getPingYin(username));
        }else {
            map.put("value", "");

        }
        return map;

    }
//    添加新用户
    @RequestMapping("addUser")
    public ResultObj addUser(UserVo userVo){
        try {


            userVo.setType(Constast.USER_TYPE_NORMAL);//设置类型
            userVo.setHiredate(new Date());
            String salt = IdUtil.simpleUUID().toUpperCase();
//            随机生成盐
            userVo.setSalt(salt);

//        设置盐
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, 2).toString());
            userService.save(userVo);
            return ResultObj.ADD_SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    @RequestMapping("loadUserById")
    public DataGridView loadUserById(Integer id){
        return new DataGridView(userService.getById(id));
    }

    @RequestMapping("updateUser")
    public ResultObj updateUser(UserVo userVo){
        try {
            userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id){
        try {
            userService.removeById(id);
            return ResultObj.UPDATE_SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("resetPwd")
    public ResultObj resetPwd(Integer id){
        try {
            User user=new User();
            user.setId(id);
//            修改id威原来的
            String salt = IdUtil.simpleUUID().toUpperCase();
//            随机生成盐
            user.setSalt(salt);

//        设置盐
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD, salt, 2).toString());
            userService.updateById(user);
            return ResultObj.RESET_SUCCESS;


        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.RESET_ERROR;
        }
    }


    /**
     * 根据用户ID查询角色并选中已拥有的角色
     * lay——check默认选中是true
     */

    @RequestMapping("initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id){
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> listMaps = roleService.listMaps(queryWrapper);
//        查询出当前所拥有的的权限
//        List<Integer> currentUserRoleIds=this.roleService.queryUserRoleIdsByUid(id);
        List<Integer> currentUserRoleIds=this.roleService.queryUserRoleIdsByUid(id);
        for (Map<String, Object> map : listMaps) {
            Boolean LAY_CHECKED=false;
            Integer roleId=(Integer) map.get("id");
            for (Integer rid : currentUserRoleIds) {
                if(rid==roleId) {
                    LAY_CHECKED=true;
                    break;
                }
            }
            map.put("LAY_CHECKED", LAY_CHECKED);
        }
        return new DataGridView(Long.valueOf(listMaps.size()), listMaps);



    }

    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer uid,Integer[] ids){
        try {
            userService.saveUserRole(uid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }

//    修改密码
    @RequestMapping("updatePwd")
    public ResultObj updatePwd(Integer id,String pwd){
//        System.out.println(id);
//        System.out.println(pwd);
        try {


//            获取原来的所有功能
            User user = new User();
            user.setId(id);
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);
            user.setPwd(new Md5Hash(pwd, salt, 2).toString());
            userService.updateById(user);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }

    }






}


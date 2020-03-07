package com.sxt.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxt.sys.common.DataGridView;
import com.sxt.sys.common.ResultObj;
import com.sxt.sys.domain.Loginfo;
import com.sxt.sys.service.LoginfoService;
import com.sxt.sys.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/loginfo")
public class LoginfoController {
    @Autowired
    LoginfoService loginfoService;


//    查询出所有的登录记录
     @RequestMapping("loadAllLoginfo")
    public DataGridView loadAllLoginfo(LoginfoVo loginfoVo){
         IPage<Loginfo> page=new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
//         限制要查的个数
//         需要要放入的集合
         QueryWrapper<Loginfo> queryWrapper=new QueryWrapper<>();
         queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
         queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
         queryWrapper.gt(loginfoVo.getStartTime()!=null, "logintime",loginfoVo.getStartTime());
         queryWrapper.lt(loginfoVo.getEndTime()!=null, "logintime",loginfoVo.getEndTime());
         queryWrapper.orderByDesc("logintime");

         //         查询条件在上方
//         为空就直接返回所有的集合

         this.loginfoService.page(page,queryWrapper);
         return new DataGridView(page.getTotal(), page.getRecords());



     }
//     删除操作
     @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(Integer id){
         try {
             loginfoService.removeById(id);
             return ResultObj.DELETE_SUCCESS;

         }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
         }
     }

//     批量删除
     @RequestMapping("batchDeleteLoginfo")
    public ResultObj batchDeleteLoginfo(LoginfoVo loginfoVo){
         try {
             Collection<Serializable> collection=new ArrayList<>();
             for (Integer id :
                     loginfoVo.getIds()) {
                 collection.add(id);
             }
             loginfoService.removeByIds(collection);
             return ResultObj.DELETE_SUCCESS;
         }catch (Exception e){
             e.printStackTrace();
             return ResultObj.DELETE_ERROR;
         }

     }

}

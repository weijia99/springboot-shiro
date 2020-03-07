package com.sxt.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxt.sys.common.Constast;
import com.sxt.sys.common.DataGridView;
import com.sxt.sys.common.ResultObj;
import com.sxt.sys.common.TreeNode;
import com.sxt.sys.domain.Dept;
import com.sxt.sys.domain.Dept;
import com.sxt.sys.service.DeptService;
import com.sxt.sys.vo.DeptVo;
import com.sxt.sys.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    DeptService deptService;




//    查询出所有的部门
    @RequestMapping("loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo){
        List<Dept> list = deptService.list();
        List<TreeNode> treeNodelist=new ArrayList<>();
        for (Dept dept :
                list) {
            boolean spread=dept.getOpen()== Constast.OPEN_TRUE ? true : false;
            treeNodelist.add(new TreeNode(dept.getId(),dept.getPid(),dept.getTitle(),spread));



        }
        return new DataGridView(treeNodelist);
    }

    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo){
        IPage<Dept> page=new Page<>(deptVo.getPage(),deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()), "title",deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()),"remark",deptVo.getRemark());

        queryWrapper.eq(deptVo.getId()!=null, "id",deptVo.getId()).or().eq(deptVo.getId()!=null, "pid",deptVo.getId());
        queryWrapper.orderByDesc("ordernum");


        deptService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());

    }

    @RequestMapping("addDept")
    public ResultObj addDept(DeptVo deptVo){
        try {


            deptVo.setCreatetime(new Date());
            deptService.save(deptVo);

            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    @RequestMapping("updateDept")
    public ResultObj updateDept(DeptVo deptVo){
        try {



            deptService.updateById(deptVo);

            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }

    }


//    返回排序号最大的
    @RequestMapping("loadDeptMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){

        Map<String,Object> map=new HashMap<>();
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<Dept> list = deptService.list(queryWrapper);
        if (list.size()>0){
            map.put("value", list.get(0).getOrdernum()+1);

        }else {
            map.put("value", 1);
        }
        return map;

    }

//    是否有子节点
    @RequestMapping("checkDeptHasChildrenNode")
    public Map<String,Object> checkDeptHasChildrenNode(DeptVo deptVo){
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid", deptVo.getId());
        List<Dept> list = deptService.list(queryWrapper);
        Map<String,Object> map = new HashMap<>();
        if (list.size()>0){
            map.put("value", true);
        }else {
            map.put("value", false);
        }
        return map;
    }

    @RequestMapping("deleteDept")
    public ResultObj deleteDept(DeptVo deptVo){
        try {
            deptService.removeById(deptVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }




}

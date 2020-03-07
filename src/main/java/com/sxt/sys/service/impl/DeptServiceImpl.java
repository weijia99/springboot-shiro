package com.sxt.sys.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.sys.domain.Dept;
import com.sxt.sys.mapper.DeptMapper;
import com.sxt.sys.service.DeptService;

/**
 * <p>
 *  服务实现类
 * </p>
 *

 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
	
//	用来生成缓存

	@Override
	public Dept getOne(Wrapper<Dept> queryWrapper) {
		return super.getOne(queryWrapper);
	}
	
	@Override
	public boolean updateById(Dept entity) {
		// TODO Auto-generated method stub
		return super.updateById(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}


	@Override
	public boolean save(Dept entity) {
		return super.save(entity);
	}

	@Override
	public Dept getById(Serializable id) {
		return super.getById(id);
	}
}

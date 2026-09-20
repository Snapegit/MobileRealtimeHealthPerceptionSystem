package com.cl.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cl.utils.PageUtils;
import com.cl.utils.Query;


import com.cl.dao.TongzhijingbaoDao;
import com.cl.entity.TongzhijingbaoEntity;
import com.cl.service.TongzhijingbaoService;
import com.cl.entity.view.TongzhijingbaoView;

@Service("tongzhijingbaoService")
public class TongzhijingbaoServiceImpl extends ServiceImpl<TongzhijingbaoDao, TongzhijingbaoEntity> implements TongzhijingbaoService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TongzhijingbaoEntity> page = this.selectPage(
                new Query<TongzhijingbaoEntity>(params).getPage(),
                new EntityWrapper<TongzhijingbaoEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<TongzhijingbaoEntity> wrapper) {
		  Page<TongzhijingbaoView> page =new Query<TongzhijingbaoView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<TongzhijingbaoView> selectListView(Wrapper<TongzhijingbaoEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public TongzhijingbaoView selectView(Wrapper<TongzhijingbaoEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}

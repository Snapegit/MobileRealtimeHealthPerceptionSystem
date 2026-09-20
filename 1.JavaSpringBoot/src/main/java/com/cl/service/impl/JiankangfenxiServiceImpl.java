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


import com.cl.dao.JiankangfenxiDao;
import com.cl.entity.JiankangfenxiEntity;
import com.cl.service.JiankangfenxiService;
import com.cl.entity.view.JiankangfenxiView;

@Service("jiankangfenxiService")
public class JiankangfenxiServiceImpl extends ServiceImpl<JiankangfenxiDao, JiankangfenxiEntity> implements JiankangfenxiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JiankangfenxiEntity> page = this.selectPage(
                new Query<JiankangfenxiEntity>(params).getPage(),
                new EntityWrapper<JiankangfenxiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JiankangfenxiEntity> wrapper) {
		  Page<JiankangfenxiView> page =new Query<JiankangfenxiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<JiankangfenxiView> selectListView(Wrapper<JiankangfenxiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JiankangfenxiView selectView(Wrapper<JiankangfenxiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}

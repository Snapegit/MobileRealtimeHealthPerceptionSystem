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


import com.cl.dao.JiankangxinxiDao;
import com.cl.entity.JiankangxinxiEntity;
import com.cl.service.JiankangxinxiService;
import com.cl.entity.view.JiankangxinxiView;

@Service("jiankangxinxiService")
public class JiankangxinxiServiceImpl extends ServiceImpl<JiankangxinxiDao, JiankangxinxiEntity> implements JiankangxinxiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JiankangxinxiEntity> page = this.selectPage(
                new Query<JiankangxinxiEntity>(params).getPage(),
                new EntityWrapper<JiankangxinxiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JiankangxinxiEntity> wrapper) {
		  Page<JiankangxinxiView> page =new Query<JiankangxinxiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<JiankangxinxiView> selectListView(Wrapper<JiankangxinxiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JiankangxinxiView selectView(Wrapper<JiankangxinxiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}

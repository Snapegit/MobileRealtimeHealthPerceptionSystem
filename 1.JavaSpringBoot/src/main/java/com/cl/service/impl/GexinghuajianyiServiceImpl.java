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


import com.cl.dao.GexinghuajianyiDao;
import com.cl.entity.GexinghuajianyiEntity;
import com.cl.service.GexinghuajianyiService;
import com.cl.entity.view.GexinghuajianyiView;

@Service("gexinghuajianyiService")
public class GexinghuajianyiServiceImpl extends ServiceImpl<GexinghuajianyiDao, GexinghuajianyiEntity> implements GexinghuajianyiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<GexinghuajianyiEntity> page = this.selectPage(
                new Query<GexinghuajianyiEntity>(params).getPage(),
                new EntityWrapper<GexinghuajianyiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<GexinghuajianyiEntity> wrapper) {
		  Page<GexinghuajianyiView> page =new Query<GexinghuajianyiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
	@Override
	public List<GexinghuajianyiView> selectListView(Wrapper<GexinghuajianyiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public GexinghuajianyiView selectView(Wrapper<GexinghuajianyiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}

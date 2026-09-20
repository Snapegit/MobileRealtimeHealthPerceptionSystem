package com.cl.dao;

import com.cl.entity.GexinghuajianyiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.GexinghuajianyiView;


/**
 * 个性化建议
 * 
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
public interface GexinghuajianyiDao extends BaseMapper<GexinghuajianyiEntity> {
	
	List<GexinghuajianyiView> selectListView(@Param("ew") Wrapper<GexinghuajianyiEntity> wrapper);

	List<GexinghuajianyiView> selectListView(Pagination page,@Param("ew") Wrapper<GexinghuajianyiEntity> wrapper);
	
	GexinghuajianyiView selectView(@Param("ew") Wrapper<GexinghuajianyiEntity> wrapper);
	

}

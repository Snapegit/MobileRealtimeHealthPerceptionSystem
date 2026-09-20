package com.cl.dao;

import com.cl.entity.JiankangfenxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiankangfenxiView;


/**
 * 健康分析
 * 
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
public interface JiankangfenxiDao extends BaseMapper<JiankangfenxiEntity> {
	
	List<JiankangfenxiView> selectListView(@Param("ew") Wrapper<JiankangfenxiEntity> wrapper);

	List<JiankangfenxiView> selectListView(Pagination page,@Param("ew") Wrapper<JiankangfenxiEntity> wrapper);
	
	JiankangfenxiView selectView(@Param("ew") Wrapper<JiankangfenxiEntity> wrapper);
	

}

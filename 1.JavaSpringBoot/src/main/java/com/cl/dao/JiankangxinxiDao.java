package com.cl.dao;

import com.cl.entity.JiankangxinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiankangxinxiView;


/**
 * 健康信息
 * 
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
public interface JiankangxinxiDao extends BaseMapper<JiankangxinxiEntity> {
	
	List<JiankangxinxiView> selectListView(@Param("ew") Wrapper<JiankangxinxiEntity> wrapper);

	List<JiankangxinxiView> selectListView(Pagination page,@Param("ew") Wrapper<JiankangxinxiEntity> wrapper);
	
	JiankangxinxiView selectView(@Param("ew") Wrapper<JiankangxinxiEntity> wrapper);
	

}

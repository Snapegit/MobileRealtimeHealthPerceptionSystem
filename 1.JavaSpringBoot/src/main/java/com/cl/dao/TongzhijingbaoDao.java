package com.cl.dao;

import com.cl.entity.TongzhijingbaoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.TongzhijingbaoView;


/**
 * 通知警报
 * 
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
public interface TongzhijingbaoDao extends BaseMapper<TongzhijingbaoEntity> {
	
	List<TongzhijingbaoView> selectListView(@Param("ew") Wrapper<TongzhijingbaoEntity> wrapper);

	List<TongzhijingbaoView> selectListView(Pagination page,@Param("ew") Wrapper<TongzhijingbaoEntity> wrapper);
	
	TongzhijingbaoView selectView(@Param("ew") Wrapper<TongzhijingbaoEntity> wrapper);
	

}

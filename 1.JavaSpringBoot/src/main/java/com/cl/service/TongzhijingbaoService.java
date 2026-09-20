package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.TongzhijingbaoEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.TongzhijingbaoView;


/**
 * 通知警报
 *
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
public interface TongzhijingbaoService extends IService<TongzhijingbaoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<TongzhijingbaoView> selectListView(Wrapper<TongzhijingbaoEntity> wrapper);
   	
   	TongzhijingbaoView selectView(@Param("ew") Wrapper<TongzhijingbaoEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<TongzhijingbaoEntity> wrapper);
   	

}


package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.JiankangfenxiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiankangfenxiView;


/**
 * 健康分析
 *
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
public interface JiankangfenxiService extends IService<JiankangfenxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JiankangfenxiView> selectListView(Wrapper<JiankangfenxiEntity> wrapper);
   	
   	JiankangfenxiView selectView(@Param("ew") Wrapper<JiankangfenxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JiankangfenxiEntity> wrapper);
   	

}


package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.JiankangxinxiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.JiankangxinxiView;


/**
 * 健康信息
 *
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
public interface JiankangxinxiService extends IService<JiankangxinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JiankangxinxiView> selectListView(Wrapper<JiankangxinxiEntity> wrapper);
   	
   	JiankangxinxiView selectView(@Param("ew") Wrapper<JiankangxinxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JiankangxinxiEntity> wrapper);
   	

}


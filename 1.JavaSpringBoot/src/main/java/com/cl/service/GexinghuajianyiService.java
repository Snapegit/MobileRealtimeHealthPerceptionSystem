package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.GexinghuajianyiEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.GexinghuajianyiView;


/**
 * 个性化建议
 *
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
public interface GexinghuajianyiService extends IService<GexinghuajianyiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<GexinghuajianyiView> selectListView(Wrapper<GexinghuajianyiEntity> wrapper);
   	
   	GexinghuajianyiView selectView(@Param("ew") Wrapper<GexinghuajianyiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<GexinghuajianyiEntity> wrapper);
   	

}


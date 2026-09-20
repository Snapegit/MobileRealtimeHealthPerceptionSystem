package com.cl.entity.view;

import com.cl.entity.GexinghuajianyiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.cl.utils.EncryptUtil;
 

/**
 * 个性化建议
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
@TableName("gexinghuajianyi")
public class GexinghuajianyiView  extends GexinghuajianyiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public GexinghuajianyiView(){
	}
 
 	public GexinghuajianyiView(GexinghuajianyiEntity gexinghuajianyiEntity){
 	try {
			BeanUtils.copyProperties(this, gexinghuajianyiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}

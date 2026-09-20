package com.cl.entity.view;

import com.cl.entity.JiankangxinxiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.cl.utils.EncryptUtil;
 

/**
 * 健康信息
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
@TableName("jiankangxinxi")
public class JiankangxinxiView  extends JiankangxinxiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public JiankangxinxiView(){
	}
 
 	public JiankangxinxiView(JiankangxinxiEntity jiankangxinxiEntity){
 	try {
			BeanUtils.copyProperties(this, jiankangxinxiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}

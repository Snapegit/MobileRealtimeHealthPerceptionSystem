package com.cl.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 健康分析
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
@TableName("jiankangfenxi")
public class JiankangfenxiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public JiankangfenxiEntity() {
		
	}
	
	public JiankangfenxiEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 主键id
	 */
	@TableId
	private Long id;
	/**
	 * 账号
	 */
					
	private String zhanghao;
	
	/**
	 * 姓名
	 */
					
	private String xingming;
	
	/**
	 * 性别
	 */
					
	private String xingbie;
	
	/**
	 * 年龄
	 */
					
	private Integer nianling;
	
	/**
	 * 分析图
	 */
					
	private String fenxitu;
	
	/**
	 * 异常检测
	 */
					
	private String yichangjiance;
	
	/**
	 * 健康报告
	 */
					
	private String jiankangbaogao;
	
	/**
	 * 分析内容
	 */
					
	private String fenxineirong;
	
	/**
	 * 分析日期
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat 		
	private Date fenxiriqi;
	
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 设置：账号
	 */
	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}
	/**
	 * 获取：账号
	 */
	public String getZhanghao() {
		return zhanghao;
	}
	/**
	 * 设置：姓名
	 */
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	/**
	 * 获取：姓名
	 */
	public String getXingming() {
		return xingming;
	}
	/**
	 * 设置：性别
	 */
	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}
	/**
	 * 获取：性别
	 */
	public String getXingbie() {
		return xingbie;
	}
	/**
	 * 设置：年龄
	 */
	public void setNianling(Integer nianling) {
		this.nianling = nianling;
	}
	/**
	 * 获取：年龄
	 */
	public Integer getNianling() {
		return nianling;
	}
	/**
	 * 设置：分析图
	 */
	public void setFenxitu(String fenxitu) {
		this.fenxitu = fenxitu;
	}
	/**
	 * 获取：分析图
	 */
	public String getFenxitu() {
		return fenxitu;
	}
	/**
	 * 设置：异常检测
	 */
	public void setYichangjiance(String yichangjiance) {
		this.yichangjiance = yichangjiance;
	}
	/**
	 * 获取：异常检测
	 */
	public String getYichangjiance() {
		return yichangjiance;
	}
	/**
	 * 设置：健康报告
	 */
	public void setJiankangbaogao(String jiankangbaogao) {
		this.jiankangbaogao = jiankangbaogao;
	}
	/**
	 * 获取：健康报告
	 */
	public String getJiankangbaogao() {
		return jiankangbaogao;
	}
	/**
	 * 设置：分析内容
	 */
	public void setFenxineirong(String fenxineirong) {
		this.fenxineirong = fenxineirong;
	}
	/**
	 * 获取：分析内容
	 */
	public String getFenxineirong() {
		return fenxineirong;
	}
	/**
	 * 设置：分析日期
	 */
	public void setFenxiriqi(Date fenxiriqi) {
		this.fenxiriqi = fenxiriqi;
	}
	/**
	 * 获取：分析日期
	 */
	public Date getFenxiriqi() {
		return fenxiriqi;
	}

}

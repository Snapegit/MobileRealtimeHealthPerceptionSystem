package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;

import com.cl.entity.JiankangfenxiEntity;
import com.cl.entity.view.JiankangfenxiView;

import com.cl.service.JiankangfenxiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;
import java.io.IOException;

/**
 * 健康分析
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
@RestController
@RequestMapping("/jiankangfenxi")
public class JiankangfenxiController {
    @Autowired
    private JiankangfenxiService jiankangfenxiService;



    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JiankangfenxiEntity jiankangfenxi,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			jiankangfenxi.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<JiankangfenxiEntity> ew = new EntityWrapper<JiankangfenxiEntity>();

		PageUtils page = jiankangfenxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiankangfenxi), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JiankangfenxiEntity jiankangfenxi, 
		HttpServletRequest request){
        EntityWrapper<JiankangfenxiEntity> ew = new EntityWrapper<JiankangfenxiEntity>();

		PageUtils page = jiankangfenxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiankangfenxi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JiankangfenxiEntity jiankangfenxi){
       	EntityWrapper<JiankangfenxiEntity> ew = new EntityWrapper<JiankangfenxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jiankangfenxi, "jiankangfenxi")); 
        return R.ok().put("data", jiankangfenxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JiankangfenxiEntity jiankangfenxi){
        EntityWrapper< JiankangfenxiEntity> ew = new EntityWrapper< JiankangfenxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jiankangfenxi, "jiankangfenxi")); 
		JiankangfenxiView jiankangfenxiView =  jiankangfenxiService.selectView(ew);
		return R.ok("查询健康分析成功").put("data", jiankangfenxiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JiankangfenxiEntity jiankangfenxi = jiankangfenxiService.selectById(id);
		jiankangfenxi = jiankangfenxiService.selectView(new EntityWrapper<JiankangfenxiEntity>().eq("id", id));
        return R.ok().put("data", jiankangfenxi);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JiankangfenxiEntity jiankangfenxi = jiankangfenxiService.selectById(id);
		jiankangfenxi = jiankangfenxiService.selectView(new EntityWrapper<JiankangfenxiEntity>().eq("id", id));
        return R.ok().put("data", jiankangfenxi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JiankangfenxiEntity jiankangfenxi, HttpServletRequest request){
    	jiankangfenxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jiankangfenxi);
        jiankangfenxiService.insert(jiankangfenxi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody JiankangfenxiEntity jiankangfenxi, HttpServletRequest request){
    	jiankangfenxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jiankangfenxi);
        jiankangfenxiService.insert(jiankangfenxi);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody JiankangfenxiEntity jiankangfenxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jiankangfenxi);
        jiankangfenxiService.updateById(jiankangfenxi);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        jiankangfenxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	








}

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

import com.cl.entity.TongzhijingbaoEntity;
import com.cl.entity.view.TongzhijingbaoView;

import com.cl.service.TongzhijingbaoService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;
import java.io.IOException;

/**
 * 通知警报
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
@RestController
@RequestMapping("/tongzhijingbao")
public class TongzhijingbaoController {
    @Autowired
    private TongzhijingbaoService tongzhijingbaoService;



    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TongzhijingbaoEntity tongzhijingbao,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			tongzhijingbao.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<TongzhijingbaoEntity> ew = new EntityWrapper<TongzhijingbaoEntity>();

		PageUtils page = tongzhijingbaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhijingbao), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TongzhijingbaoEntity tongzhijingbao, 
		HttpServletRequest request){
        EntityWrapper<TongzhijingbaoEntity> ew = new EntityWrapper<TongzhijingbaoEntity>();

		PageUtils page = tongzhijingbaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhijingbao), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( TongzhijingbaoEntity tongzhijingbao){
       	EntityWrapper<TongzhijingbaoEntity> ew = new EntityWrapper<TongzhijingbaoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( tongzhijingbao, "tongzhijingbao")); 
        return R.ok().put("data", tongzhijingbaoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TongzhijingbaoEntity tongzhijingbao){
        EntityWrapper< TongzhijingbaoEntity> ew = new EntityWrapper< TongzhijingbaoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( tongzhijingbao, "tongzhijingbao")); 
		TongzhijingbaoView tongzhijingbaoView =  tongzhijingbaoService.selectView(ew);
		return R.ok("查询通知警报成功").put("data", tongzhijingbaoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TongzhijingbaoEntity tongzhijingbao = tongzhijingbaoService.selectById(id);
		tongzhijingbao = tongzhijingbaoService.selectView(new EntityWrapper<TongzhijingbaoEntity>().eq("id", id));
        return R.ok().put("data", tongzhijingbao);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TongzhijingbaoEntity tongzhijingbao = tongzhijingbaoService.selectById(id);
		tongzhijingbao = tongzhijingbaoService.selectView(new EntityWrapper<TongzhijingbaoEntity>().eq("id", id));
        return R.ok().put("data", tongzhijingbao);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TongzhijingbaoEntity tongzhijingbao, HttpServletRequest request){
    	tongzhijingbao.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(tongzhijingbao);
        tongzhijingbaoService.insert(tongzhijingbao);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody TongzhijingbaoEntity tongzhijingbao, HttpServletRequest request){
    	tongzhijingbao.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(tongzhijingbao);
        tongzhijingbaoService.insert(tongzhijingbao);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody TongzhijingbaoEntity tongzhijingbao, HttpServletRequest request){
        //ValidatorUtils.validateEntity(tongzhijingbao);
        tongzhijingbaoService.updateById(tongzhijingbao);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        tongzhijingbaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	








}

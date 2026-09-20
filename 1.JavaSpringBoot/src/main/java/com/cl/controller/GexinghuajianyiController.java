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

import com.cl.entity.GexinghuajianyiEntity;
import com.cl.entity.view.GexinghuajianyiView;

import com.cl.service.GexinghuajianyiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;
import java.io.IOException;

/**
 * 个性化建议
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-25 16:12:26
 */
@RestController
@RequestMapping("/gexinghuajianyi")
public class GexinghuajianyiController {
    @Autowired
    private GexinghuajianyiService gexinghuajianyiService;



    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,GexinghuajianyiEntity gexinghuajianyi,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			gexinghuajianyi.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<GexinghuajianyiEntity> ew = new EntityWrapper<GexinghuajianyiEntity>();

		PageUtils page = gexinghuajianyiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, gexinghuajianyi), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,GexinghuajianyiEntity gexinghuajianyi, 
		HttpServletRequest request){
        EntityWrapper<GexinghuajianyiEntity> ew = new EntityWrapper<GexinghuajianyiEntity>();

		PageUtils page = gexinghuajianyiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, gexinghuajianyi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( GexinghuajianyiEntity gexinghuajianyi){
       	EntityWrapper<GexinghuajianyiEntity> ew = new EntityWrapper<GexinghuajianyiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( gexinghuajianyi, "gexinghuajianyi")); 
        return R.ok().put("data", gexinghuajianyiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(GexinghuajianyiEntity gexinghuajianyi){
        EntityWrapper< GexinghuajianyiEntity> ew = new EntityWrapper< GexinghuajianyiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( gexinghuajianyi, "gexinghuajianyi")); 
		GexinghuajianyiView gexinghuajianyiView =  gexinghuajianyiService.selectView(ew);
		return R.ok("查询个性化建议成功").put("data", gexinghuajianyiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        GexinghuajianyiEntity gexinghuajianyi = gexinghuajianyiService.selectById(id);
		gexinghuajianyi = gexinghuajianyiService.selectView(new EntityWrapper<GexinghuajianyiEntity>().eq("id", id));
        return R.ok().put("data", gexinghuajianyi);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        GexinghuajianyiEntity gexinghuajianyi = gexinghuajianyiService.selectById(id);
		gexinghuajianyi = gexinghuajianyiService.selectView(new EntityWrapper<GexinghuajianyiEntity>().eq("id", id));
        return R.ok().put("data", gexinghuajianyi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody GexinghuajianyiEntity gexinghuajianyi, HttpServletRequest request){
    	gexinghuajianyi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(gexinghuajianyi);
        gexinghuajianyiService.insert(gexinghuajianyi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody GexinghuajianyiEntity gexinghuajianyi, HttpServletRequest request){
    	gexinghuajianyi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(gexinghuajianyi);
        gexinghuajianyiService.insert(gexinghuajianyi);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody GexinghuajianyiEntity gexinghuajianyi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(gexinghuajianyi);
        gexinghuajianyiService.updateById(gexinghuajianyi);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        gexinghuajianyiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	








}

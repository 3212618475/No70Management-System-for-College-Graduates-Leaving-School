package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
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
import com.annotation.IgnoreAuth;

import com.entity.BiyezhengxinxiEntity;
import com.entity.view.BiyezhengxinxiView;

import com.service.BiyezhengxinxiService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 毕业证信息
 * 后端接口
 * @author 
 * @email 
 * @date 2022-07-23 06:34:11
 */
@RestController
@RequestMapping("/biyezhengxinxi")
public class BiyezhengxinxiController {
    @Autowired
    private BiyezhengxinxiService biyezhengxinxiService;


    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,BiyezhengxinxiEntity biyezhengxinxi,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("biyesheng")) {
			biyezhengxinxi.setXuehao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("fudaoyuan")) {
			biyezhengxinxi.setZhigongzhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<BiyezhengxinxiEntity> ew = new EntityWrapper<BiyezhengxinxiEntity>();
		PageUtils page = biyezhengxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, biyezhengxinxi), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,BiyezhengxinxiEntity biyezhengxinxi, 
		HttpServletRequest request){
        EntityWrapper<BiyezhengxinxiEntity> ew = new EntityWrapper<BiyezhengxinxiEntity>();
		PageUtils page = biyezhengxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, biyezhengxinxi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( BiyezhengxinxiEntity biyezhengxinxi){
       	EntityWrapper<BiyezhengxinxiEntity> ew = new EntityWrapper<BiyezhengxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( biyezhengxinxi, "biyezhengxinxi")); 
        return R.ok().put("data", biyezhengxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(BiyezhengxinxiEntity biyezhengxinxi){
        EntityWrapper< BiyezhengxinxiEntity> ew = new EntityWrapper< BiyezhengxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( biyezhengxinxi, "biyezhengxinxi")); 
		BiyezhengxinxiView biyezhengxinxiView =  biyezhengxinxiService.selectView(ew);
		return R.ok("查询毕业证信息成功").put("data", biyezhengxinxiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        BiyezhengxinxiEntity biyezhengxinxi = biyezhengxinxiService.selectById(id);
        return R.ok().put("data", biyezhengxinxi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        BiyezhengxinxiEntity biyezhengxinxi = biyezhengxinxiService.selectById(id);
        return R.ok().put("data", biyezhengxinxi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BiyezhengxinxiEntity biyezhengxinxi, HttpServletRequest request){
    	biyezhengxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(biyezhengxinxi);
        biyezhengxinxiService.insert(biyezhengxinxi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody BiyezhengxinxiEntity biyezhengxinxi, HttpServletRequest request){
    	biyezhengxinxi.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(biyezhengxinxi);
        biyezhengxinxiService.insert(biyezhengxinxi);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody BiyezhengxinxiEntity biyezhengxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(biyezhengxinxi);
        biyezhengxinxiService.updateById(biyezhengxinxi);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        biyezhengxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<BiyezhengxinxiEntity> wrapper = new EntityWrapper<BiyezhengxinxiEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("biyesheng")) {
			wrapper.eq("xuehao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("fudaoyuan")) {
			wrapper.eq("zhigongzhanghao", (String)request.getSession().getAttribute("username"));
		}

		int count = biyezhengxinxiService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	





    @RequestMapping("/importExcel")
    public R importExcel(@RequestParam("file") MultipartFile file){
        try {
            //获取输入流
            InputStream inputStream = file.getInputStream();
            //创建读取工作簿
            Workbook workbook = WorkbookFactory.create(inputStream);
            //获取工作表
            Sheet sheet = workbook.getSheetAt(0);
            //获取总行
            int rows=sheet.getPhysicalNumberOfRows();
            if(rows>1){
                //获取单元格
                for (int i = 1; i < rows; i++) {
                    Row row = sheet.getRow(i);
                    BiyezhengxinxiEntity biyezhengxinxiEntity =new BiyezhengxinxiEntity();
                    biyezhengxinxiEntity.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
                    String xuehao = CommonUtil.getCellValue(row.getCell(0));
                    biyezhengxinxiEntity.setXuehao(xuehao);
                    String xingming = CommonUtil.getCellValue(row.getCell(1));
                    biyezhengxinxiEntity.setXingming(xingming);
                    String shifoufafang = CommonUtil.getCellValue(row.getCell(2));
                    biyezhengxinxiEntity.setShifoufafang(shifoufafang);
                    String weifafangyuanyin = CommonUtil.getCellValue(row.getCell(3));
                    biyezhengxinxiEntity.setWeifafangyuanyin(weifafangyuanyin);
                    String shenheqingkuang = CommonUtil.getCellValue(row.getCell(4));
                    biyezhengxinxiEntity.setShenheqingkuang(shenheqingkuang);
                    String shenheyijian = CommonUtil.getCellValue(row.getCell(5));
                    biyezhengxinxiEntity.setShenheyijian(shenheyijian);
                    String zhigongzhanghao = CommonUtil.getCellValue(row.getCell(6));
                    biyezhengxinxiEntity.setZhigongzhanghao(zhigongzhanghao);
                    String zhigongxingming = CommonUtil.getCellValue(row.getCell(7));
                    biyezhengxinxiEntity.setZhigongxingming(zhigongxingming);
                     
                    //想数据库中添加新对象
                    biyezhengxinxiService.insert(biyezhengxinxiEntity);//方法
                }
            }
            inputStream.close();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok("导入成功");
    }


}

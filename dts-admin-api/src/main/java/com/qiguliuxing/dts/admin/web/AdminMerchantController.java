package com.qiguliuxing.dts.admin.web;

import static com.qiguliuxing.dts.admin.util.AdminResponseCode.ADMIN_INVALID_NAME;
import static com.qiguliuxing.dts.admin.util.AdminResponseCode.ADMIN_INVALID_PASSWORD;
import static com.qiguliuxing.dts.admin.util.AdminResponseCode.ADMIN_NAME_EXIST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.qiguliuxing.dts.db.dao.DtsMerchantMapper;
import com.qiguliuxing.dts.db.domain.DtsMerchant;
import com.qiguliuxing.dts.db.service.DtsMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qiguliuxing.dts.admin.annotation.RequiresPermissionsDesc;
import com.qiguliuxing.dts.admin.util.AdminResponseUtil;
import com.qiguliuxing.dts.core.util.RegexUtil;
import com.qiguliuxing.dts.core.util.ResponseUtil;
import com.qiguliuxing.dts.core.util.bcrypt.BCryptPasswordEncoder;
import com.qiguliuxing.dts.core.validator.Order;
import com.qiguliuxing.dts.core.validator.Sort;
import com.qiguliuxing.dts.db.domain.DtsAdmin;
import com.qiguliuxing.dts.db.service.DtsAdminService;

@Api(tags = "商家增删改查")
@RestController
@RequestMapping("/admin/merchant")
@Validated
public class AdminMerchantController {
    private static final Logger logger = LoggerFactory.getLogger(AdminMerchantController.class);

    @Autowired
    private DtsMerchantService dtsMerchantService;

    @Autowired
    private DtsMerchantMapper dtsMerchantMapper;

    @ApiOperation(value = "添加新商家")
    @PostMapping("addAdminMerchant")
    public Object addAdminMerchant(@RequestBody DtsMerchant dtsMerchant) {

        System.out.println(dtsMerchant);
        int insert = dtsMerchantService.add(dtsMerchant);
        if(insert>0){
            logger.info("AdminMerchantController 添加商户成功 : "+dtsMerchant);
            return ResponseUtil.ok();
        }else{
            logger.info("AdminMerchantController 添加商户失败 : "+dtsMerchant);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(value = "添加商家")
    @PostMapping("addMerchant")
    public Object addMerchant(@RequestBody DtsMerchant dtsMerchant) {


        int insert = dtsMerchantMapper.addMerchant(dtsMerchant);
        if(insert>0){
            logger.info("AdminMerchantController 添加商户成功 : "+dtsMerchant);
            return ResponseUtil.ok();
        }else{
            logger.info("AdminMerchantController 添加商户失败 : "+dtsMerchant);
            return ResponseUtil.fail();
        }
    }
    @ApiOperation(value = "根据Id获取商家信息")
    @GetMapping("getMerchantInfo/{id}")
    public Object getChapterInfo(@PathVariable int id) {
        if(id>0){
            DtsMerchant dtsMerchant = dtsMerchantService.selectById(id);
            System.out.println(dtsMerchant);
            return  ResponseUtil.ok(dtsMerchant);
        }else{
            return ResponseUtil.fail();
        }


    }


    @ApiOperation(value = "修改商家")
    @PostMapping("updateMerchant")
    public Object updateChapter(@RequestBody DtsMerchant dtsMerchant) {
        System.out.println(dtsMerchant);
        Boolean i = dtsMerchantService.updateById_role(dtsMerchant);
        if(i){
            logger.info("updateMerchant修改成功 : "+dtsMerchant);
            return ResponseUtil.ok();
        }else {
            logger.info("updateMerchant修改失败 : "+dtsMerchant);
            return ResponseUtil.fail();
        }

    }


    @ApiOperation(value = "修改商家 Admin方法")
    @PostMapping("updateMerchantAdmin")
    public Object updateMerchant(@RequestBody DtsMerchant dtsMerchant) {
        System.out.println(dtsMerchant);
        Boolean i = dtsMerchantService.updateByPrimaryKeySelective(dtsMerchant);
        if(i){
            logger.info("updateMerchant修改成功 : "+dtsMerchant);
            return ResponseUtil.ok();
        }else {
            logger.info("updateMerchant修改失败 : "+dtsMerchant);
            return ResponseUtil.fail();
        }

    }
    @ApiOperation(value = "所有商家列表")
    @GetMapping("listMerchant")
    public Object findAll() {
        List<DtsMerchant> list = dtsMerchantService.selectByAll();

        Map<String, Object> data = new HashMap<>();
        data.put("items", list);
        //调用service的方法实现查询所有的操作
        //Map<String, Object> list = dtsMerchantService.selectByAll(null);

        System.out.println(data);
        return ResponseUtil.ok(data);
    }

    @ApiOperation(value = "删除商家")
    @GetMapping("deleteMerchant/{id}")
    public Object deleteById(@PathVariable int id) {
            System.out.println(id);
        int i = dtsMerchantService.deleteById(id);

        if (i>0){
                logger.info("AdminMerchantController 逻辑删除数据成功 ：" +id);
                return ResponseUtil.ok();
            }else{
                logger.info("AdminMerchantController 逻辑删除数据失败 ：" +id);
                return  ResponseUtil.fail();
            }

    }


}

package com.qiguliuxing.dts.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiguliuxing.dts.db.dao.DtsMerchantMapper;
import com.qiguliuxing.dts.db.domain.DtsAdmin;
import com.qiguliuxing.dts.db.domain.DtsAdminExample;
import com.qiguliuxing.dts.db.domain.DtsMerchant;
import com.qiguliuxing.dts.db.service.DtsMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DtsMerchantimpl  extends ServiceImpl<DtsMerchantMapper, DtsMerchant> implements DtsMerchantService {

    @Autowired
    DtsMerchantMapper dtsMerchantMapper;


    @Override
    public int add(DtsMerchant dtsMerchant) {
        return dtsMerchantMapper.insertSelective(dtsMerchant);
    }

    public List<DtsMerchant> selectByAll() {

        return dtsMerchantMapper.selectByAll();


    }

    @Override
    public int deleteById(Integer id) {
        return dtsMerchantMapper.logicalDeleteByPrimaryKey(id);
    }

    @Override
    public DtsMerchant selectMerchantById(int id) {
        return dtsMerchantMapper.selectById(id);
    }

    @Override
    public boolean updateById_role(DtsMerchant dtsMerchant) {
        return dtsMerchantMapper.updateById_role(dtsMerchant);
    }

    @Override
    public Boolean updateByPrimaryKeySelective(DtsMerchant dtsMerchant) {
        return dtsMerchantMapper.updateByPrimaryKeySelective(dtsMerchant);
    }

    @Override
    public int addMerchant(DtsMerchant dtsMerchant) {
        return dtsMerchantMapper.addMerchant(dtsMerchant);
    }

    @Override
    public List<DtsMerchant> findMerchant(String username) {


            return dtsMerchantMapper.selectByName(username);

    }


}

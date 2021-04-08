package com.qiguliuxing.dts.db.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qiguliuxing.dts.db.domain.DtsMerchant;

import java.util.List;


public interface DtsMerchantService extends IService<DtsMerchant> {


    int add(DtsMerchant dtsMerchant);

    List<DtsMerchant> selectByAll();
    int  deleteById(Integer id);

    DtsMerchant selectMerchantById(int id);

    boolean updateById_role(DtsMerchant dtsMerchant);
    Boolean updateByPrimaryKeySelective(DtsMerchant dtsMerchant);
    int addMerchant(DtsMerchant dtsMerchant);

    List<DtsMerchant> findMerchant(String username);
}

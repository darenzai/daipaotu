package com.qiguliuxing.dts.db.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiguliuxing.dts.db.domain.DtsMerchant;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DtsMerchantMapper extends BaseMapper<DtsMerchant> {


    List<DtsMerchant> selectByAll();

    int logicalDeleteByPrimaryKey(Integer id);

    Boolean updateById_role(DtsMerchant dtsMerchant);

    Boolean updateByPrimaryKeySelective(DtsMerchant dtsMerchant);

    int insertSelective(DtsMerchant dtsMerchant);
    int addMerchant(DtsMerchant dtsMerchant);
}

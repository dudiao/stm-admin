package com.github.dudiao.stm.admin.service;

import cn.hutool.core.util.StrUtil;
import com.github.dudiao.stm.admin.model.StmApp;
import com.github.dudiao.stm.admin.utils.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;

import java.util.List;

/**
 * @author songyinyin
 * @since 2023/5/4 15:37
 */
@Service
public class ApiService {

    @Autowired
    private EruptDao eruptDao;

    private static final String APP_ORDER_BY = " order by weight asc, createTime desc";

    public List<StmApp> appList(String name) {
        if (StrUtil.isNotBlank(name)) {
            return eruptDao.queryEntityList(StmApp.class, "name like :name" + APP_ORDER_BY, MapBuilder.of("name", name));
        }
        return eruptDao.queryEntityList(StmApp.class, APP_ORDER_BY, MapBuilder.of("name", name));
    }
}

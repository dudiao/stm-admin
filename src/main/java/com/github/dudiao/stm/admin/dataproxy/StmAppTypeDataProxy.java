package com.github.dudiao.stm.admin.dataproxy;

import com.github.dudiao.stm.admin.model.StmApp;
import com.github.dudiao.stm.admin.model.StmAppType;
import com.github.dudiao.stm.admin.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.DataProxy;

/**
 * @author songyinyin
 * @since 2023/5/10 11:49
 */
@Component
public class StmAppTypeDataProxy implements DataProxy<StmAppType> {

    @Autowired
    private ApiService apiService;

    @Override
    public void beforeAdd(StmAppType stmAppType) {
        apiService.runtimeCacheClear();
    }

    @Override
    public void beforeUpdate(StmAppType stmAppType) {
        apiService.runtimeCacheClear();
    }

    @Override
    public void beforeDelete(StmAppType stmAppType) {
        apiService.runtimeCacheClear();
    }
}

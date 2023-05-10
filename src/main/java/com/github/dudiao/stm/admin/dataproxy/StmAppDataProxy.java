package com.github.dudiao.stm.admin.dataproxy;

import com.github.dudiao.stm.admin.model.StmApp;
import com.github.dudiao.stm.admin.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.DataProxy;

/**
 * @author songyinyin
 * @since 2023/5/10 11:49
 */
@Component
public class StmAppDataProxy implements DataProxy<StmApp> {

    @Autowired
    private ApiService apiService;

    @Override
    public void beforeAdd(StmApp stmApp) {
        apiService.appCacheClear();
    }

    @Override
    public void beforeUpdate(StmApp stmApp) {
        apiService.appCacheClear();
    }

    @Override
    public void beforeDelete(StmApp stmApp) {
        apiService.appCacheClear();
    }
}

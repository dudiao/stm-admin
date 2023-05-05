package com.github.dudiao.stm.admin.controller;

import com.github.dudiao.stm.admin.base.Result;
import com.github.dudiao.stm.admin.base.StmConstants;
import com.github.dudiao.stm.admin.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.jpa.dao.EruptJpaDao;

/**
 * @author songyinyin
 * @since 2023/5/4 12:12
 */
@RestController
@RequestMapping(StmConstants.API_PATH + "/v1")
public class ApiController {


    @Autowired
    private ApiService apiService;


    @GetMapping("/list")
    public Result list(@RequestParam String name) {

        return Result.ok(apiService.appList(name));
    }
}

package com.github.dudiao.stm.admin.controller;

import com.github.dudiao.stm.admin.base.Result;
import com.github.dudiao.stm.admin.base.StmConstants;
import com.github.dudiao.stm.admin.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songyinyin
 * @since 2023/5/4 12:12
 */
@RestController
@RequestMapping(StmConstants.API_PATH + "/v1")
public class ApiController {


    @Autowired
    private ApiService apiService;

    /**
     * 查看所有应用
     */
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) String name) {

        return Result.ok(apiService.appList(name));
    }

    /**
     * 获取应用的最新版本信息
     */
    @GetMapping("/latestVersion")
    public Result latestVersion(@RequestParam String appName,
                                @RequestParam(required = false) String version) {
        return Result.ok(apiService.latestVersion(appName, version));
    }

    /**
     * 获取应用运行时环境的下载地址，比如 jre
     *
     * @param appType 应用类型，现在只支持 Java
     */
    @GetMapping("/getAppRuntimeSdkUrls")
    public Result getAppRuntimeSdkUrls(@RequestParam String appType, @RequestParam(defaultValue = "17") Long appTypeVersion,
                                   @RequestParam String osName, @RequestParam String osArch) {
        return Result.ok(apiService.getAppRuntimeSdkUrls(appType, appTypeVersion, osName, osArch));
    }
}

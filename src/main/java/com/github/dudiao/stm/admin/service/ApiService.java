package com.github.dudiao.stm.admin.service;

import cn.hutool.core.util.StrUtil;
import com.github.dudiao.stm.admin.base.BusinessException;
import com.github.dudiao.stm.admin.model.StmApp;
import com.github.dudiao.stm.admin.model.StmAppType;
import com.github.dudiao.stm.admin.model.StmAppVersion;
import com.github.dudiao.stm.admin.repository.StmAppRepository;
import com.github.dudiao.stm.admin.utils.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.erupt.jpa.dao.EruptDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author songyinyin
 * @since 2023/5/4 15:37
 */
@Slf4j
@Service
public class ApiService {

    @Autowired
    private EruptDao eruptDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StmAppRepository stmAppRepository;

    private static final String APP_ORDER_BY = " order by a.weight asc, a.createTime desc";
    private static final String APP_SELECT = """
        select new map(a.id as id, a.name as name, a.author as author, a.appType as appType, a.status as status, 
        max(av.version) as version, 
        a.requiredAppTypeVersionNum as requiredAppTypeVersionNum, a.description as description) from StmApp a left join a.appVersions av
        """;

    @Cacheable(value = "appList", unless = "#result.size() == 0", key = "#name == null ? '_all' : #name")
    public List<Map<String, Object>> appList(String name) {
        Query query;
        if (StrUtil.isNotBlank(name)) {
            query = entityManager.createQuery(APP_SELECT + " where a.name like :name" + APP_ORDER_BY)
                .setParameter("name", "%" + name + "%");
        } else {
            query = entityManager.createQuery(APP_SELECT + APP_ORDER_BY);
        }
        query.setFirstResult(0);
        query.setMaxResults(20);
        return query.getResultList();
    }

    @Cacheable(value = "latestVersion", key = "#appName + '::' + #version")
    public StmApp latestVersion(String appName, String version) {

        StmApp stmApp = stmAppRepository.findByName(appName);
        if (stmApp == null) {
            throw new BusinessException("应用不存在");
        }

        StmAppVersion stmAppVersion;
        if (StrUtil.isBlank(version)) {
            stmAppVersion = stmApp.getAppVersions().get(0);
        } else {
            stmAppVersion = eruptDao.queryEntity(StmAppVersion.class, "stm_app_id = :appId and version = :version",
                MapBuilder.of("appId", stmApp.getId(), "version", version));
        }
        if (stmAppVersion == null) {
            throw new BusinessException("应用版本不存在");
        }

        stmApp.setAppVersions(null);
        stmApp.setAppLatestVersion(stmAppVersion);
        return stmApp;
    }

    @Cacheable(value = "getAppRuntimeSdkUrls", key = "#appType + '::' + #appTypeVersion + '::' + #osName + '_' + #osArch")
    public List<String> getAppRuntimeSdkUrls(String appType, Long appTypeVersion, String osName, String osArch) {
        String expr = "appType = :appType and appTypeVersionNum = :appTypeVersionNum and osName = :osName and osArch = :osArch";
        Map<String, Object> map = new HashMap<>();
        map.put("appType", appType);
        map.put("appTypeVersionNum", appTypeVersion);
        map.put("osName", formatOsName(osName));
        map.put("osArch", osArch);
        List<StmAppType> stmAppTypes = eruptDao.queryEntityList(StmAppType.class, expr, map);
        return stmAppTypes.stream().map(StmAppType::getDownloadUrl).collect(Collectors.toList());
    }

    private String formatOsName(String osName) {
        osName = osName.toLowerCase();
        if (osName.contains("window")) {
            return "windows";
        } else if (osName.contains("mac")) {
            return "mac";
        } else if (osName.contains("linux")) {
            return "linux";
        } else {
            return "other";
        }
    }

    @CacheEvict(value = {"appList", "latestVersion"}, allEntries = true)
    public void appCacheClear() {
        log.info("stmApp and stmAppVersion cache clear");
    }

    @CacheEvict(value = {"getAppRuntimeSdkUrls"}, allEntries = true)
    public void runtimeCacheClear() {
        log.info("stmAppType cache clear");
    }

}

package com.github.dudiao.stm.admin.service;

import cn.hutool.core.util.StrUtil;
import com.github.dudiao.stm.admin.model.StmAppType;
import com.github.dudiao.stm.admin.model.StmAppVersion;
import com.github.dudiao.stm.admin.utils.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
public class ApiService {

    @Autowired
    private EruptDao eruptDao;

    @PersistenceContext
    private EntityManager entityManager;

    private static final String APP_ORDER_BY = " order by a.weight asc, a.createTime desc";
    private static final String APP_SELECT = """
    select new map(a.id as id, a.name as name, a.author as author, a.appType as appType, a.status as status, 
    max(av.version) as version, 
    a.requiredAppTypeVersionNum as requiredAppTypeVersionNum, a.description as description) from StmApp a left join a.appVersions av
    """;

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

    public StmAppVersion latestVersion(String appId, String version) {

        if (StrUtil.isBlank(version)) {
            return eruptDao.queryEntity(StmAppVersion.class, "stm_app_id = :appId order by versionNum desc", MapBuilder.of("appId", appId));
        }
        return eruptDao.queryEntity(StmAppVersion.class, "stm_app_id = :appId and version = :version",
            MapBuilder.of("appId", appId, "version", version));

    }

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

}

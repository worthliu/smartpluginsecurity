package com.worth.smart.filter;

import com.worth.smart.config.SecurityConfig;
import com.worth.smart.constant.SecurityConstant;
import com.worth.smart.realm.SmartCustomRealm;
import com.worth.smart.realm.SmartJdbcRealm;
import com.worth.smart.security.SmartSecurity;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @ClassName SmartSecurityFilter
 * @Description TODO
 * @Author Administrator
 * @Date 2018/10/7 10:57
 * @Version 1.0
 */
public class SmartSecurityFilter extends ShiroFilter {
    @Override
    public void init() throws Exception {
        super.init();
        WebSecurityManager webSecurityManager = super.getSecurityManager();
        //設置Realm,可同時支持多個Realm,并按照先後順序用逗號分割
        setRealms(webSecurityManager);
        //設置Cache,用於減少數據庫查詢次數,減低IO訪問
        setCache(webSecurityManager);
    }

    private void setCache(WebSecurityManager webSecurityManager) {
        String securityRealms = SecurityConfig.getRealms();
        if (securityRealms != null) {
            //
            String[] securityRealmArray = securityRealms.split(",");
            if (securityRealmArray.length > 0) {
                //
                Set<Realm> realms = new LinkedHashSet<>();
                for (String securityRealm : securityRealmArray) {
                    if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)) {
                        addJdbcRealm(realms);
                    } else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)) {
                        addCustomRealm(realms);
                    }
                }
                RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
                realmSecurityManager.setRealms(realms);
            }
        }
    }

    private void addCustomRealm(Set<Realm> realms) {
        //读取smart.plugin.security.custom.class配置项
        SmartSecurity smartSecurity = SecurityConfig.getSmartSecurity();
        SmartCustomRealm smartCustomRealm = new SmartCustomRealm(smartSecurity);
        realms.add(smartCustomRealm);
    }

    private void addJdbcRealm(Set<Realm> realms) {
        // 添加自己实现的基于JDBC的Realm
        SmartJdbcRealm smartJdbcRealm = new SmartJdbcRealm();
        realms.add(smartJdbcRealm);
    }

    private void setRealms(WebSecurityManager webSecurityManager) {
        //读取smart.plugin.security.cache配置项
        if (SecurityConfig.isCache()) {
            CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
            // 使用基于内存的CacheManager
            CacheManager cacheManager = new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);
        }
    }
}

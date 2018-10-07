package com.worth.smart.config;

import com.worth.smart.constant.SecurityConstant;
import com.worth.smart.framework.common.utils.PropsUtil;
import com.worth.smart.framework.common.utils.ReflectionUtil;
import com.worth.smart.security.SmartSecurity;

/**
 * @ClassName SecurityConfig
 * @Description 从配置文件中获取相关属性
 * @Author Administrator
 * @Date 2018/10/7 11:04
 * @Version 1.0
 */
public class SecurityConfig {
    public static String getRealms() {
        return PropsUtil.getString(null, SecurityConstant.REALMS_CUSTOM);
    }

    public static boolean isCache() {
        return PropsUtil.getBoolean(null, SecurityConstant.CACHE);
    }

    public static String getJdbcRolesQuery() {
        return PropsUtil.getString(null, SecurityConstant.JDBC_ROLES_QUERY);
    }

    public static String getJdbcAuthQuery() {
        return PropsUtil.getString(null, SecurityConstant.JDBC_AUTH_QUERY);
    }

    public static String getJdbcPermissionsQuery() {
        return PropsUtil.getString(null, SecurityConstant.JDBC_PERMISSIONS_QUERY);
    }

    /**
     * @return
     */
    public static SmartSecurity getSmartSecurity() {
        String className = PropsUtil.getString(null, SecurityConstant.SMART_SECURITY);
        return (SmartSecurity) ReflectionUtil.newInstance(className);
    }
}

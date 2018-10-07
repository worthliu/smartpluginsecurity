package com.worth.smart.constant;

/**
 * @ClassName SecurityConstant
 * @Description TODO
 * @Author Administrator
 * @Date 2018/10/7 11:05
 * @Version 1.0
 */
public interface SecurityConstant {
    String REALMS = "smart.plugin.security.realms";
    String REALMS_JDBC = "jdbc";
    String REALMS_CUSTOM = "custom";
    String SMART_SECURITY = "smart.plugin.security.custom.class";
    String JDBC_AUTH_QUERY = "smart.plugin.security.jdbc.auth_query";
    String JDBC_ROLES_QUERY = "smart.plugin.security.jdbc.roles_query";
    String JDBC_PERMISSIONS_QUERY = "smart.plugin.security.jdbc.permissions_query";
    String CACHE = "smart.plugin.security.cache";
}

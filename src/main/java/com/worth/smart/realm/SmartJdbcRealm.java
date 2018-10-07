package com.worth.smart.realm;

import com.worth.smart.config.SecurityConfig;
import com.worth.smart.framework.common.helper.DatabaseHelper;
import com.worth.smart.matcher.Md5CredentialsMatcher;
import org.apache.shiro.realm.jdbc.JdbcRealm;

/**
 * @ClassName SmartJdbcRealm
 * @Description TODO
 * @Author Administrator
 * @Date 2018/10/7 11:21
 * @Version 1.0
 */
public class SmartJdbcRealm extends JdbcRealm {
    public SmartJdbcRealm() {
        super.setDataSource(DatabaseHelper.getDataSource());
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthQuery());
        super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
        super.setPermissionsQuery(SecurityConfig.getJdbcPermissionsQuery());
        super.setPermissionsLookupEnabled(true);
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }
}

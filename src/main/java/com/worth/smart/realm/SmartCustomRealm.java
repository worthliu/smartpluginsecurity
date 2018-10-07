package com.worth.smart.realm;

import com.worth.smart.config.SecurityConfig;
import com.worth.smart.constant.SecurityConstant;
import com.worth.smart.matcher.Md5CredentialsMatcher;
import com.worth.smart.security.SmartSecurity;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SmartCustomRealm
 * @Description TODO
 * @Author Administrator
 * @Date 2018/10/7 11:39
 * @Version 1.0
 */
public class SmartCustomRealm extends AuthorizingRealm {

    private final SmartSecurity smartSecurity;

    public SmartCustomRealm(SmartSecurity smartSecurity) {
        this.smartSecurity = smartSecurity;
        super.setName(SecurityConstant.REALMS_CUSTOM);
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if(principals == null){
            throw new AuthenticationException("parameter principals is null");
        }
        //获取已认证用户的用户名
        String username = (String) super.getAvailablePrincipal(principals);
        //通过SmartSecurity接口并根据用户名获取角色名集合
        Set<String> roleNameSet = smartSecurity.getRoleNameSet(username);
        //通过SmartSecurity接口并根据角色获取与其对应的权限名集合
        Set<String> permissionNameSet = new HashSet<>();
        if (roleNameSet != null && roleNameSet.size() > 0){
            for (String roleName : roleNameSet){
                Set<String> currentPermissionNameSet = smartSecurity.getPermissionNameSet(roleName);
                permissionNameSet.addAll(currentPermissionNameSet);
            }
        }
        // 将角色名集合与权限集合放入AuthorizationInfo对象中,便于后续的授权操作
        SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
        authenticationInfo.setRoles(roleNameSet);
        authenticationInfo.setStringPermissions(permissionNameSet);
        return authenticationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token == null){
            throw new AuthenticationException("parameter token is null");
        }

        // 通过AuthenticationToken对象获取从表单中提交过来的用户名
        String username = ((UsernamePasswordToken)token).getUsername();
        // 通过SmartSecurity接口并根据用户名获取数据库中存放的密码
        String password = smartSecurity.getPassword(username);
        // 将用户名与密码放入AuthenticationInfo对象中,便于后续的认证操作
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        authenticationInfo.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
        authenticationInfo.setCredentials(password);
        //
        return authenticationInfo;
    }
}

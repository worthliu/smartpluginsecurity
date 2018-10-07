package com.worth.smart.helper;

import com.worth.smart.framework.common.helper.ServletHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName SecurityHelper
 * @Description TODO
 * @Author Administrator
 * @Date 2018/10/7 19:04
 * @Version 1.0
 */
public final class SecurityHelper {
    private static final Logger logger = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * @param username
     * @param password
     */
    public static void login(String username, String password){
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null){
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                currentUser.login(token);
            }catch (AuthenticationException e){
                logger.error("login failure", e);
                throw new AuthenticationException(e);
            }
        }
    }

    /**
     *
     */
    public static void logout(){
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null){
            currentUser.logout();
        }
    }
}

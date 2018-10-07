package com.worth.smart.plugin;

import com.worth.smart.filter.SmartSecurityFilter;
import org.apache.shiro.web.env.EnvironmentLoaderListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @ClassName SmartSecurityPlugin
 * @Description Smart Security 插件
 * @Author Administrator
 * @Date 2018/10/7 10:52
 * @Version 1.0
 */
public class SmartSecurityPlugin implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        //設置初始化參數
        ctx.setInitParameter("shiroConfigLocations", "classpath:smart-security.ini");
        //注冊Listener
        ctx.addListener(EnvironmentLoaderListener.class);
        //注冊Filter
        FilterRegistration.Dynamic smartSecurityFilter = ctx.addFilter("SmartSecurityFilter", SmartSecurityFilter.class);
        smartSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}

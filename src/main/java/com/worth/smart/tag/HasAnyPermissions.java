package com.worth.smart.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * @ClassName HasAnyPermissions
 * @Description TODO
 * @Author Administrator
 * @Date 2018/10/7 19:43
 * @Version 1.0
 */
public class HasAnyPermissions extends PermissionTag {
    @Override
    protected boolean showTagBody(String permissions) {
        boolean hasAnyPermissions = false;
        Subject subject = getSubject();
        if (subject != null){
            if (subject.isPermitted(permissions)){
                hasAnyPermissions = true;
            }
        }
        return hasAnyPermissions;
    }
}

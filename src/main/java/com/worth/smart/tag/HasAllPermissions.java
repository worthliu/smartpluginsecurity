package com.worth.smart.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * @ClassName HasAllPermissions
 * @Description TODO
 * @Author Administrator
 * @Date 2018/10/7 19:45
 * @Version 1.0
 */
public class HasAllPermissions extends PermissionTag {
    private static final String PERMISSIONS_NAMES_DELIMITER = ",";

    @Override
    protected boolean showTagBody(String permissionsNames) {
        boolean hasAllPermissions = false;
        Subject subject = getSubject();
        if(subject != null){
            if (subject.isPermittedAll(permissionsNames.split(PERMISSIONS_NAMES_DELIMITER))){
                hasAllPermissions = true;
            }
        }
        return hasAllPermissions;
    }
}

package com.worth.smart.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.RoleTag;

import java.util.Arrays;

/**
 * @ClassName HasAllRolesTag
 * @Description TODO
 * @Author Administrator
 * @Date 2018/10/7 19:37
 * @Version 1.0
 */
public class HasAllRolesTag extends RoleTag {
    private static final String ROLE_NAMES_DELIMITER = ",";

    @Override
    protected boolean showTagBody(String roleName) {
        boolean hasAllRole = false;
        Subject subject = getSubject();
        if (subject != null){
            if (subject.hasAllRoles(Arrays.asList(roleName.split(ROLE_NAMES_DELIMITER)))){
                hasAllRole = true;
            }
        }
        return hasAllRole;
    }
}

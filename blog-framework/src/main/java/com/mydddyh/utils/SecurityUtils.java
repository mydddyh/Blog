package com.mydddyh.utils;

import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isSuperAdmin(){
        Long id = getLoginUser().getUser().getId();
        return SystemConstants.SUPER_ADMIN_USER_ID.equals(id);
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}

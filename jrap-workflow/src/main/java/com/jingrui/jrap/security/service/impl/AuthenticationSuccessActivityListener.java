package com.jingrui.jrap.security.service.impl;

import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.service.IUserService;
import com.jingrui.jrap.activiti.listeners.TaskCreateNotificationListener;
import com.jingrui.jrap.security.IAuthenticationSuccessListener;
import com.jingrui.jrap.system.service.impl.BadgeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录成功时，activity代办事项.
 *
 * @author lijian.yin@jingrui.com
 * @date 2018/2/27
 **/

@Component
public class AuthenticationSuccessActivityListener implements IAuthenticationSuccessListener {

    @Autowired
    private TaskCreateNotificationListener taskCreateNotificationListener;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        HttpSession session = request.getSession(true);
        Long employeeId = (Long) session.getAttribute(User.FIELD_EMPLOYEE_ID);
        if (employeeId != null) {
            String userName = (String) session.getAttribute(User.FIELD_USER_NAME);
            Object codes = redisTemplate.opsForHash().get(BadgeServiceImpl.HAP_CACHE_BADGE, userName);
            if (codes == null) {
                String employeeCode = (String) session.getAttribute(User.FIELD_EMPLOYEE_CODE);
                String employeeName = (String) session.getAttribute(User.FIELD_EMPLOYEE_NAME);
                User user = new User();
                user.setEmployeeCode(employeeCode);
                user.setEmployeeName(employeeName);
                user.setUserName(userName);
                taskCreateNotificationListener.sendMessage(user);
                taskCreateNotificationListener.sendMessageForCC(user);
            }
        }
    }
}

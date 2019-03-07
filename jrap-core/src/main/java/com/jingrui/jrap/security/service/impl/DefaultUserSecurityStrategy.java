package com.jingrui.jrap.security.service.impl;

import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.exception.UserException;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.components.SysConfigManager;
import com.jingrui.jrap.mybatis.util.StringUtil;
import com.jingrui.jrap.security.IUserSecurityStrategy;
import com.jingrui.jrap.security.PasswordManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 用户安全策略默认实现类.
 * <p>
 *
 * @author Qixiangyu on 2016/12/22.
 * @author njq.niu@jingrui.com
 */
@Component
public class DefaultUserSecurityStrategy implements IUserSecurityStrategy {

    private static final Pattern PASSWORD_PATTERN_DIGITS_AND_LETTERS = Pattern.compile("(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]+");
    private static final Pattern PASSWORD_PATTERN_DIGITS_AND_CASE_LETTERS = Pattern.compile("(?!^\\d+$)(?!^[a-z]+$)(?!^[A-Z]+$)(?!^[\\dA-Z]+$)(?!^[\\da-z]+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]+");

    public static final String PASSWORD_UPDATE_REASON = "PASSWORD_UPDATE_REASON";
    public static final String PASSWORD_UPDATE_REASON_EXPIRED = "EXPIRED";
    public static final String PASSWORD_UPDATE_REASON_RESET = "RESET";


    @Autowired
    private PasswordManager passwordManager;

    @Autowired
    SysConfigManager sysConfigManager;

    @Override
    public ModelAndView loginVerifyStrategy(User user, HttpServletRequest request) {

        String reason = null;

        // 判断密码是否失效
        if (passwordManager.getPasswordInvalidTime() > 0 && user.getLastPasswordUpdateDate() != null
                && daysBetween(user.getLastPasswordUpdateDate(), new Date()) >= passwordManager.getPasswordInvalidTime()) {
            reason = PASSWORD_UPDATE_REASON_EXPIRED;
        }

        // 是否第一次登陆判断是否需要修改密码
        if (sysConfigManager.getResetPwFlag() && StringUtil.isNotEmpty(user.getFirstLogin()) && BaseConstants.YES.equalsIgnoreCase(user.getFirstLogin())) {
            reason = PASSWORD_UPDATE_REASON_RESET;
        }

        if (StringUtil.isNotEmpty(reason)) {
            HttpSession session = request.getSession(false);
            session.setAttribute(PASSWORD_UPDATE_REASON, reason);
            return new ModelAndView(BaseConstants.VIEW_REDIRECT + "/password/reset");
        }
        return null;
    }

    @Override
    public void validatePassword(String newPwd, String newPwdAgain) throws UserException {
        if (StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(newPwdAgain)) {
            throw new UserException(UserException.PASSWORD_NOT_EMPTY, null);
        }

        if (newPwd != null && !newPwd.equals(newPwdAgain)) {
            throw new UserException(UserException.USER_PASSWORD_NOT_SAME_TWICE, null);
        }

        Integer length = passwordManager.getPasswordMinLength();
        if (newPwd.length() < length) {
            throw new UserException(UserException.USER_PASSWORD_LENGTH_INSUFFICIENT, new Integer[]{length});
        }

        String complexity = passwordManager.getPasswordComplexity();
        if (PasswordManager.PASSWORD_COMPLEXITY_NO_LIMIT.equalsIgnoreCase(complexity)) {
        } else if (PasswordManager.PASSWORD_COMPLEXITY_DIGITS_AND_LETTERS.equalsIgnoreCase(complexity) && !PASSWORD_PATTERN_DIGITS_AND_LETTERS.matcher(newPwd).matches()) {
            throw new UserException(UserException.USER_PASSWORD_REQUIREMENT_DIGITS_AND_LETTERS, null);
        } else if (PasswordManager.PASSWORD_COMPLEXITY_DIGITS_AND_CASE_LETTERS.equalsIgnoreCase(complexity) && !PASSWORD_PATTERN_DIGITS_AND_CASE_LETTERS.matcher(newPwd).matches()) {
            throw new UserException(UserException.USER_PASSWORD_REQUIREMENT_DIGITS_AND_CASE_LETTERS, null);
        }
    }

    @Override
    public int getOrder() {
        return 9999;
    }

    /**
     * 两时间相差毫秒数.
     *
     * @param dateStart 开始时间
     * @param dateEnd   结束时间
     * @return 毫秒
     */
    private int daysBetween(Date dateStart, Date dateEnd) {
        return (int) ((dateEnd.getTime() - dateStart.getTime()) / (1000 * 3600 * 24));
    }
}

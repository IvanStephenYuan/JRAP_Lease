package com.jingrui.jrap.security;

import java.util.Arrays;
import java.util.List;

import com.jingrui.jrap.mybatis.util.StringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.jingrui.jrap.message.profile.SystemConfigListener;

/**
 * @author njq.niu@jingrui.com
 * @author xiangyu.qi@jingrui.com
 * @date 2016/1/31
 * @date 2016/10/10
 */
public class PasswordManager implements PasswordEncoder, InitializingBean, SystemConfigListener {

    public static final String PASSWORD_COMPLEXITY_NO_LIMIT = "NO_LIMIT";
    public static final String PASSWORD_COMPLEXITY_DIGITS_AND_LETTERS = "DIGITS_AND_LETTERS";
    public static final String PASSWORD_COMPLEXITY_DIGITS_AND_CASE_LETTERS = "DIGITS_AND_CASE_LETTERS";

    private PasswordEncoder delegate;

    private String siteWideSecret = "my-secret-key";

    private String defaultPassword = "123456";

    /**
     * 密码失效时间 默认0 不失效
     */
    private Integer passwordInvalidTime = 0;

    /**
     * 密码长度
     */
    private Integer passwordMinLength = 8;

    /**
     * 密码复杂度
     */
    private String passwordComplexity = "no_limit";

    public Integer getPasswordInvalidTime() {
        return passwordInvalidTime;
    }

    public Integer getPasswordMinLength() {
        return passwordMinLength;
    }


    public String getPasswordComplexity() {
        return passwordComplexity;
    }


    public String getDefaultPassword() {
        return defaultPassword;
    }


    public String getSiteWideSecret() {
        return siteWideSecret;
    }

    public void setSiteWideSecret(String siteWideSecret) {
        this.siteWideSecret = siteWideSecret;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        delegate = new StandardPasswordEncoder(siteWideSecret);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return delegate.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (StringUtil.isEmpty(encodedPassword)) {
            return false;
        }
        return delegate.matches(rawPassword, encodedPassword);
    }

    @Override
    public List<String> getAcceptedProfiles() {
        return Arrays.asList("DEFAULT_PASSWORD", "PASSWORD_INVALID_TIME", "PASSWORD_MIN_LENGTH", "PASSWORD_COMPLEXITY");
    }

    @Override
    public void updateProfile(String profileName, String profileValue) {
        if ("PASSWORD_INVALID_TIME".equalsIgnoreCase(profileName)) {
            this.passwordInvalidTime = Integer.parseInt(profileValue);
        } else if ("PASSWORD_MIN_LENGTH".equalsIgnoreCase(profileName)) {
            this.passwordMinLength = Integer.parseInt(profileValue);
        } else if ("PASSWORD_COMPLEXITY".equalsIgnoreCase(profileName)) {
            this.passwordComplexity = profileValue;
        } else if ("DEFAULT_PASSWORD".equalsIgnoreCase(profileName)) {
            this.defaultPassword = profileValue;
        }
    }
}

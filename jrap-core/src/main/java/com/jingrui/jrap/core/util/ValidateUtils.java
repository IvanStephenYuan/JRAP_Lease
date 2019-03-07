package com.jingrui.jrap.core.util;

import com.jingrui.jrap.core.BaseConstants;

/**
 * 格式校验工具类.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/12/26
 **/
public class ValidateUtils {

    /**
     * 校验邮箱格式.
     *
     * @param email 邮箱
     * @return 判定结果
     */
    public static boolean validateEmail(String email) {
        return BaseConstants.PATTERN_EMAIL_REGEX.matcher(email).matches();
    }

    /**
     * 校验电话格式.
     *
     * @param phone 电话
     * @return 判定结果
     */
    public static boolean validatePhone(String phone) {
        return BaseConstants.PATTERN_PHONE_REGEX.matcher(phone).matches();
    }

    /**
     * 校验用户名格式.
     *
     * @param userName
     * @return
     */
    public static boolean validateUserName(String userName) {
        return BaseConstants.UESR_NAME_REGEX.matcher(userName).matches();
    }

}

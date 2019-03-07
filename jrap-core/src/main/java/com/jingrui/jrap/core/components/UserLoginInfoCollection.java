package com.jingrui.jrap.core.components;

import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.mybatis.util.StringUtil;
import com.jingrui.jrap.security.CustomUserDetails;
import com.jingrui.jrap.security.IAuthenticationSuccessListener;
import com.jingrui.jrap.system.dto.UserLogin;
import com.jingrui.jrap.system.mapper.UserLoginMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by jialong.zuo@jingrui.com on 2016/10/11. on 2016/10/11.
 */
@Component
public class UserLoginInfoCollection implements IAuthenticationSuccessListener {

    @Autowired
    IMessagePublisher iMessagePublisher;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {

        String ipAddress = getIpAddress(request);

        UserLogin userLogin = new UserLogin();
        userLogin.setUserId((Long)request.getSession(false).getAttribute(User.FIELD_USER_ID));
        userLogin.setReferer(StringUtils.abbreviate(request.getHeader("Referer"),240));
        userLogin.setUserAgent(StringUtils.abbreviate(request.getHeader("User-Agent"), 240));
        userLogin.setIp(ipAddress);
        userLogin.setLoginTime(new Date());
        iMessagePublisher.message("jrap:queue:loginInfo", userLogin);
    }

    @Override
    public int getOrder() {
        return 999;
    }

    public static String getIpAddress(HttpServletRequest request) {

        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;

    }

}

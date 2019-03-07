package com.jingrui.jrap.message.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jingrui.jrap.core.AppContextInitListener;
import com.jingrui.jrap.mybatis.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.TopicMonitor;
import com.jingrui.jrap.message.profile.GlobalProfileListener;
import com.jingrui.jrap.message.profile.ListenerInitHandler;
import com.jingrui.jrap.message.profile.SystemConfigListener;
import com.jingrui.jrap.system.dto.GlobalProfile;
import com.jingrui.jrap.system.service.IProfileService;
import com.jingrui.jrap.system.service.ISysConfigService;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Component
@TopicMonitor(channel = { GlobalProfileSubscriber.PROFILE, GlobalProfileSubscriber.CONFIG })
public class GlobalProfileSubscriber implements IMessageConsumer<GlobalProfile>, ListenerInitHandler, AppContextInitListener {

    public static final String PROFILE = "profile";

    public static final String CONFIG = "config";

    private static Map<GlobalProfileListener, List<String>> listenerMap = new HashMap<>();

    @Autowired
    private IProfileService profileService;

    @Autowired
    private ISysConfigService configService;

    private Logger logger = LoggerFactory.getLogger(GlobalProfileSubscriber.class);

    public void addListener(GlobalProfileListener listener) {
        listenerMap.put(listener, listener.getAcceptedProfiles());
        initLoad(listener);
    }

    @Override
    public void initLoad(GlobalProfileListener listener) {
        if (logger.isDebugEnabled()) {
            logger.debug("initial load profile values for:" + listener);
        }
        // 初始化 系统配置
        if (listener instanceof SystemConfigListener) {
            for (String configCode : listener.getAcceptedProfiles()) {
                String configValue = configService.getConfigValue(configCode);
                if (StringUtil.isNotEmpty(configValue)) {
                    listener.updateProfile(configCode, configValue);
                }
            }
        } else {
            IRequest iRequest = RequestHelper.newEmptyRequest();
            iRequest.setUserId(-1L);
            iRequest.setRoleId(-1L);
            for (String profileName : listener.getAcceptedProfiles()) {
                String profileValue = profileService.getProfileValue(iRequest, profileName);
                if (StringUtil.isNotEmpty(profileValue)) {
                    listener.updateProfile(profileName, profileValue);
                }
            }
        }
    }

    @Override
    public void onMessage(GlobalProfile message, String pattern) {
        listenerMap.forEach((k, v) -> {
            // channel=config notify SystemConfig
            if (CONFIG.equalsIgnoreCase(pattern)) {
                if (k instanceof SystemConfigListener) {
                    if (v.contains(message.getProfileName())) {
                        k.updateProfile(message.getProfileName(), message.getProfileValue());
                    }
                }
            } else if (PROFILE.equalsIgnoreCase(pattern)) {
                if (!(k instanceof SystemConfigListener)){
                    if (v.contains(message.getProfileName())) {
                        k.updateProfile(message.getProfileName(), message.getProfileValue());
                    }
                }
            }
        });
    }

    /**
     * find all GlobalProfileListener .
     * 
     * @throws Exception
     */
    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        Map<String, GlobalProfileListener> listeners = applicationContext.getBeansOfType(GlobalProfileListener.class);
        listeners.forEach((k, v) -> addListener(v));
    }

}

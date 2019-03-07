package com.jingrui.jrap.security.service.impl;

import com.jingrui.jrap.core.AppContextInitListener;
import com.jingrui.jrap.security.IUserSecurityStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Qixiangyu
 * @date 2016/12/22.
 */
@Component
public class UserSecurityStrategyManager implements AppContextInitListener {

    List<IUserSecurityStrategy> userSecurityStrategyList = Collections.emptyList();
    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        userSecurityStrategyList= new ArrayList<>(applicationContext.getBeansOfType(IUserSecurityStrategy.class).values());
        Collections.sort(userSecurityStrategyList);
    }

    public  List<IUserSecurityStrategy> getUserSecurityStrategyList(){
        return userSecurityStrategyList;
    }
}

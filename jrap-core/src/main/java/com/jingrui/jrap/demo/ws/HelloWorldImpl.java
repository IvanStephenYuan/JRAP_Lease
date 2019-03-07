/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.demo.ws;

import com.jingrui.jrap.account.dto.User;

import javax.jws.WebService;

@WebService(endpointInterface = "com.jingrui.jrap.demo.ws.HelloWorld",serviceName="HelloGT")
public class HelloWorldImpl implements HelloWorld{  
  
    @Override  
    public User sayHello(String name, User user) {
        System.out.println(user.getUserName());
      
        user.setEmail("test@jingrui.com");
        return user;
    }  
  
}  
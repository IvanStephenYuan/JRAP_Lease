/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.demo.ws;

import com.jingrui.jrap.account.dto.User;

import javax.jws.WebService;
  

@WebService  
public interface HelloWorld {  
      
     User sayHello(String name, User user);
      
} 
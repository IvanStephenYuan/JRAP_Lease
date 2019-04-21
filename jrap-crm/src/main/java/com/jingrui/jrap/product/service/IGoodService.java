package com.jingrui.jrap.product.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.product.dto.Good;
import com.jingrui.jrap.core.IRequest;

public interface IGoodService extends IBaseService<Good>, ProxySelf<IGoodService>{

  /*启动工作流*/
  void createVacationInstance(IRequest iRequest, Good good);
}
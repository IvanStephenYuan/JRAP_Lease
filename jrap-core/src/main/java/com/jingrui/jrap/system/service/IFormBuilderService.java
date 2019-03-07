package com.jingrui.jrap.system.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.dto.Form;

import java.util.List;

public interface IFormBuilderService extends IBaseService<Form>, ProxySelf<IFormBuilderService>{

    List<Form> batchUpdate(IRequest iRequest, @StdWho List<Form> forms);

    int batchDelete(List<Form> forms);

}
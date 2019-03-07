package com.jingrui.jrap.system.service.impl;

import com.jingrui.jrap.cache.Cache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.Form;
import com.jingrui.jrap.system.service.IFormBuilderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FormBuilderServiceImpl extends BaseServiceImpl<Form> implements IFormBuilderService {

    @Autowired
    @Qualifier("formCache")
    private Cache formCache;

    @Override
    public List<Form> batchUpdate(IRequest request, List<Form> forms) {
        for (Form form : forms) {
            form.setCode(StringUtils.upperCase(form.getCode()));
            if (form.getFormId() == null) {
                self().insertSelective(request,form);
            } else if (form.getFormId() != null) {
                self().updateByPrimaryKey(request,form);

            }
            formCache.setValue(form.getCode(), form);
        }
        return forms;
    }

    public int batchDelete(List<Form> forms){
        int c = 0;
        for(Form form: forms){
            c += self().deleteByPrimaryKey(form);
            formCache.remove(form.getCode());
        }
        return c;
    }
}
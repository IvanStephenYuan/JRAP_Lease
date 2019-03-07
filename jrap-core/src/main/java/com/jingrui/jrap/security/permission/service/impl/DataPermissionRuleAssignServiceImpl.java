package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.security.permission.dto.DataPermissionRuleAssign;
import com.jingrui.jrap.security.permission.service.IDataPermissionRuleAssignService;
import com.jingrui.jrap.system.dto.Lov;
import com.jingrui.jrap.system.service.ILovService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zuojialong
 */
@Service
public class DataPermissionRuleAssignServiceImpl extends BaseServiceImpl<DataPermissionRuleAssign> implements IDataPermissionRuleAssignService {

    @Autowired
    private ILovService lovService;

    @Autowired
    IMessagePublisher iMessagePublisher;

    @Override
    public List<DataPermissionRuleAssign> selectRuleAssign(DataPermissionRuleAssign dto, int page,
                                                           int pageSize, IRequest request)
            throws IllegalAccessException {
        List<DataPermissionRuleAssign> lists = selectOptions(request, dto,null, page, pageSize);
//        for (DataPermissionRuleAssign dataMaskRuleAssign : lists) {
//            Lov lov = new Lov();
//            lov.setCode(dataMaskRuleAssign.getAssignField());
//            List<Lov> lovs = lovService.selectLovs(request, lov, 1, 10);
//            lov = lovs.get(0);
//            String textField = lov.getTextField();
//            String valueField = lov.getValueField();
//
//            Map<String, String> lovMap = new HashMap(16);
//            List<Object> list = (List<Object>) lovService.selectDatas(request, dataMaskRuleAssign.getAssignField(), null, 0, 0);
//            for (Object o : list) {
//                if(o instanceof Map){
//                    DataPermissionRuleDetailServiceImpl.getValueByMap(textField,valueField, (Map<String, String>) o,lovMap);
//                }else {
//                    DataPermissionRuleDetailServiceImpl.getValueByBean(textField, valueField, o, lovMap);
//                }
//            }
//            dataMaskRuleAssign.setAssignFieldName(lovMap.get(dataMaskRuleAssign.getAssignFieldValue()));
//        }
        return lists;
    }

    @Override
    public void removeDataMaskRuleAssign(List<DataPermissionRuleAssign> dataMaskRuleAssigns) {
        self().batchDelete(dataMaskRuleAssigns);
        updateCache(dataMaskRuleAssigns.get(0).getRuleId());
    }

    @Override
    public List<DataPermissionRuleAssign> updateDataMaskRuleAssign(IRequest request, List<DataPermissionRuleAssign> dataMaskRuleAssigns) {
        List<DataPermissionRuleAssign> dto = self().batchUpdate(request, dataMaskRuleAssigns);
        updateCache(dataMaskRuleAssigns.get(0).getRuleId());
        return dto;
    }

    private void updateCache(Long ruleId) {
        iMessagePublisher.publish("dataPermission.ruleRefresh", ruleId);
    }
}
package com.jingrui.jrap.security.permission.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.security.permission.dto.DataPermissionRuleAssign;
import com.jingrui.jrap.security.permission.mapper.DataPermissionRuleAssignMapper;
import com.jingrui.jrap.system.dto.Lov;
import com.jingrui.jrap.system.service.ILovService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.security.permission.dto.DataPermissionRuleDetail;
import com.jingrui.jrap.security.permission.service.IDataPermissionRuleDetailService;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataPermissionRuleDetailServiceImpl extends BaseServiceImpl<DataPermissionRuleDetail> implements IDataPermissionRuleDetailService {

    @Autowired
    private ILovService lovService;

    @Autowired
    DataPermissionRuleAssignMapper dataPermissionRuleAssignMapper;

    @Autowired
    IMessagePublisher iMessagePublisher;


    @Override
    public List<DataPermissionRuleDetail> selectRuleManageDetail(DataPermissionRuleDetail dto, int page, int pageSize, IRequest request) throws IllegalAccessException {
        List<DataPermissionRuleDetail> lists = selectOptions(request, dto,null, page, pageSize);
        return lists;
    }



//    public static void getValueByBean(String textField, String valueField, Object object, Map lovMap) throws IllegalAccessException {
//        String text = null;
//        String value = null;
//        Field[] fields = object.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            if (field.getName().equalsIgnoreCase(textField)) {
//                field.setAccessible(true);
//                text = field.get(object).toString();
//            }
//            if (field.getName().equalsIgnoreCase(valueField)) {
//                field.setAccessible(true);
//                value = field.get(object).toString();
//            }
//        }
//        lovMap.put(value, text);
//    }
//
//    public static void getValueByMap(String textField, String valueField, Map object, Map lovMap){
//        lovMap.put(object.get(valueField).toString(), object.get(textField));
//    }


    @Transactional(rollbackFor = Exception.class)
    public void removeDataMaskRuleDetailWithAssign(List<DataPermissionRuleDetail> dataMaskRuleManageDetails) {
        batchDelete(dataMaskRuleManageDetails);
        dataMaskRuleManageDetails.forEach(v -> {
            DataPermissionRuleAssign assign = new DataPermissionRuleAssign();
            assign.setDetailId(v.getDetailId());
            dataPermissionRuleAssignMapper.delete(assign);
        });
    }

    public List<DataPermissionRuleDetail> updateDataMaskRuleDetail(IRequest iRequest, List<DataPermissionRuleDetail> dto) {
        List<DataPermissionRuleDetail> dataMaskRuleManageDetails = self().batchUpdate(iRequest, dto);

        updateCache(dto.get(0).getRuleId());

        return dataMaskRuleManageDetails;
    }

    public void removeDataMaskRuleDetail(List<DataPermissionRuleDetail> dataMaskRuleManageDetails) {
        self().removeDataMaskRuleDetailWithAssign(dataMaskRuleManageDetails);

        updateCache(dataMaskRuleManageDetails.get(0).getRuleId());
    }

    private void updateCache(Long ruleId) {
        iMessagePublisher.publish("dataPermission.ruleRefresh", ruleId);
    }
}
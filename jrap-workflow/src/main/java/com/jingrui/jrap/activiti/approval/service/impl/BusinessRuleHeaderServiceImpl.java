package com.jingrui.jrap.activiti.approval.service.impl;

/**
 * @author xiangyu.qi@jingrui.com
 */

import com.jingrui.jrap.activiti.approval.dto.BusinessRuleHeader;
import com.jingrui.jrap.activiti.approval.dto.BusinessRuleLine;
import com.jingrui.jrap.activiti.approval.mapper.BusinessRuleHeaderMapper;
import com.jingrui.jrap.activiti.approval.mapper.BusinessRuleLineMapper;
import com.jingrui.jrap.activiti.approval.service.IBusinessRuleHeaderService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessRuleHeaderServiceImpl extends BaseServiceImpl<BusinessRuleHeader> implements IBusinessRuleHeaderService {

    @Autowired
    private BusinessRuleHeaderMapper headerMapper;

    @Autowired
    private BusinessRuleLineMapper lineMapper;


    /**
     * 批量操作头行数据.
     *
     * @param header 头行数据
     */
    private void processLines(BusinessRuleHeader header) {
        for (BusinessRuleLine line : header.getLines()) {
            if (line.getBusinessRuleId() == null) {
                line.setBusinessRuleId(header.getBusinessRuleId()); // 设置头ID跟行ID一致
                lineMapper.insertSelective(line);
            } else if (line.getBusinessRuleId() != null) {
                int updateCount = lineMapper.updateByPrimaryKeySelective(line);
                checkOvn(updateCount, header);
            }
        }
    }

    @Override
    public List<BusinessRuleHeader> batchUpdate(IRequest request, List<BusinessRuleHeader> headers) {
        for (BusinessRuleHeader header : headers) {
            if (header.getBusinessRuleId() == null) {
                self().createRule(header);
            } else if (header.getBusinessRuleId() != null) {
                self().updateRule(header);
            }
        }
        return headers;
    }

    @Override
    public boolean batchDelete(IRequest request, List<BusinessRuleHeader> headers) {
        // 删除头行
        BusinessRuleLine line = new BusinessRuleLine();
        for (BusinessRuleHeader header : headers) {
            line.setBusinessRuleId(header.getBusinessRuleId());
            // 删除行
            //lineMapper.deleteByRuleId(line);
            int count = lineMapper.delete(line);
            checkOvn(count, line);
            // 删除头
            int updateCount = headerMapper.deleteByPrimaryKey(header);
            checkOvn(updateCount, header);
        }
        return true;
    }

    @Override
    public List<BusinessRuleHeader> selectAll(IRequest request, BusinessRuleHeader header) {
        return headerMapper.select(header);
    }

    @Override
    public BusinessRuleHeader createRule(BusinessRuleHeader header) {
        // 插入头
        headerMapper.insertSelective(header);
        // 判断如果行不为空，则迭代循环插入
        if (header.getLines() != null) {
            processLines(header);
        }

        return null;
    }

    @Override
    public BusinessRuleHeader updateRule(BusinessRuleHeader header) {
        //headerMapper.updateByPrimaryKeySelective(header);
        int count = headerMapper.updateByPrimaryKey(header);
        checkOvn(count, header);
        // 判断如果行不为空，则迭代循环插入
        if (header.getLines() != null) {
            processLines(header);
        }
        return header;
    }

}
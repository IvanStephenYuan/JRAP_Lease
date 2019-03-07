package com.jingrui.jrap.fnd.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.fnd.dto.Company;
import com.jingrui.jrap.fnd.service.ICompanyService;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.system.dto.DTOStatus;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公司服务接口实现.
 *
 * @author jialong.zuo@jingrui.com
 * @date 2016/10/9.
 */
@Service
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements ICompanyService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Company> batchUpdate(IRequest request, List<Company> list) {
        Criteria criteria = new Criteria();
        criteria.update(Company.FIELD_COMPANY_SHORT_NAME, Company.FIELD_COMPANY_FULL_NAME, Company.FIELD_COMPANY_TYPE
                , Company.FIELD_COMPANY_LEVEL_ID, Company.FIELD_PARENT_COMPANY_ID, Company.FIELD_CHIEF_POSITION_ID,
                Company.FIELD_START_DATE_ACTIVE, Company.FIELD_END_DATE_ACTIVE, Company.FIELD_ZIPCODE, Company.FIELD_FAX,
                Company.FIELD_PHONE, Company.FIELD_CONTACT_PERSON, Company.FIELD_ADDRESS);
        criteria.updateExtensionAttribute();
        for (Company company : list) {
            if (company.get__status().equalsIgnoreCase(DTOStatus.UPDATE)) {
                self().updateByPrimaryKeyOptions(request, company, criteria);
            } else {
                insertSelective(request, company);
            }
        }
        return list;
    }
}

package com.jingrui.jrap.intergration.service.impl;


import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.cache.impl.ApiConfigCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import com.jingrui.jrap.intergration.dto.JrapInterfaceLine;
import com.jingrui.jrap.intergration.mapper.JrapInterfaceHeaderMapper;
import com.jingrui.jrap.intergration.service.IJrapAuthenticationService;
import com.jingrui.jrap.intergration.service.IJrapInterfaceHeaderService;
import com.jingrui.jrap.intergration.service.IJrapInterfaceLineService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jiguang.sun@jingrui.com
 * @date 2016/7/21.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JrapInterfaceHeaderServiceImpl extends BaseServiceImpl<JrapInterfaceHeader> implements IJrapInterfaceHeaderService {

    private final Logger logger = LoggerFactory.getLogger(JrapInterfaceHeaderServiceImpl.class);

    @Autowired
    private JrapInterfaceHeaderMapper jrapInterfaceHeaderMapper;

    @Autowired
    private ApiConfigCache apiCache;


    @Autowired
    private IJrapInterfaceLineService lineService;

    @Autowired
    private IJrapAuthenticationService authenticationService;

    private static String AUTH_TYPE_OAUTH2 = "OAUTH2";


    @Override
    public List<JrapInterfaceHeader> getAllHeader(IRequest requestContext, JrapInterfaceHeader interfaceHeader, int page, int pagesize) {

        PageHelper.startPage(page, pagesize);
        return jrapInterfaceHeaderMapper.getAllHeader(interfaceHeader);
    }

    @Override
    public List<JrapInterfaceHeader> getHeaderAndLineList(IRequest requestContext ,JrapInterfaceHeader interfaceHeader) {
        List<JrapInterfaceHeader> list = jrapInterfaceHeaderMapper.getHeaderAndLineList(interfaceHeader);
        if (list.isEmpty() || list.size() < 0) {
            list = jrapInterfaceHeaderMapper.getHeaderByHeaderId(interfaceHeader);
        }
        return list;

    }

    @Override
    public JrapInterfaceHeader getHeaderAndLine(String sysName, String apiName) {
        logger.info("sysName apiName:{}", sysName + apiName);
        JrapInterfaceHeader headerAndLineDTO = apiCache.getValue(sysName +JrapInterfaceHeader.CACHE_SEPARATOR+ apiName);
        if (headerAndLineDTO == null) {
            JrapInterfaceHeader headerAndLineDTO1 = jrapInterfaceHeaderMapper.getHeaderAndLineBySysNameAndApiName(sysName, apiName);
            if (headerAndLineDTO1 != null) {
                apiCache.setValue(sysName +JrapInterfaceHeader.CACHE_SEPARATOR+ apiName, headerAndLineDTO1);
            }
            return headerAndLineDTO1;
        } else {
            return headerAndLineDTO;

        }

    }

    /*
   * 获取所有的header和line数据——> HeaderAndHeaderTlDTO
   * */
    @Override
    public List<JrapInterfaceHeader> getAllHeaderAndLine() {
        return jrapInterfaceHeaderMapper.getAllHeaderAndLine();
    }


    /*
    * 获取所有的header和line数据——> HeaderAndHeaderTlDTO(分页)
    * */
    @Override
    public List<JrapInterfaceHeader> getAllHeaderAndLine(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return jrapInterfaceHeaderMapper.getAllHeaderAndLine();
    }

    @Override
    public List<JrapInterfaceHeader> getHeaderByHeaderId(IRequest requestContext, JrapInterfaceHeader JrapInterfaceHeader) {
        return jrapInterfaceHeaderMapper.getHeaderByHeaderId(JrapInterfaceHeader);
    }

    @Override
    public JrapInterfaceHeader getHeaderAndLineByLineId(JrapInterfaceHeader headerAndLineDTO) {
        return jrapInterfaceHeaderMapper.getHeaderAndLineBylineId(headerAndLineDTO);
    }

    @Override
    public int updateHeader(IRequest request, JrapInterfaceHeader hmsInterfaceHeader) {

        int result = jrapInterfaceHeaderMapper.updateByPrimaryKeySelective(hmsInterfaceHeader);
        checkOvn(result, hmsInterfaceHeader);
        if (result > 0) {
            // 修改头，修改后重新加入缓存
            apiCache.reload();
        }
        return result;
    }

    @Override
    public void createInterface(IRequest iRequest, JrapInterfaceHeader interfaceHeader){
        interfaceHeader.setHeaderId(UUID.randomUUID().toString());
        interfaceHeader.setDescription(interfaceHeader.getName());
        JrapInterfaceHeader hapInterfaceHeaderNew = self().insertSelective(iRequest, interfaceHeader);
        if (interfaceHeader.getLineList() != null) {
            processInterfaceLines(iRequest, interfaceHeader);
        }
    }

    @Override
    public void updateInterface(IRequest iRequest, JrapInterfaceHeader interfaceHeader) {
        interfaceHeader.setDescription(interfaceHeader.getName());
        self().updateHeader(iRequest, interfaceHeader);

        if (interfaceHeader.getLineList() != null) {
            processInterfaceLines(iRequest, interfaceHeader);
        }
        if (AUTH_TYPE_OAUTH2.equalsIgnoreCase(interfaceHeader.getAuthType())) {
            authenticationService.removeToken(interfaceHeader);
        }
    }

    private void processInterfaceLines(IRequest iRequest, JrapInterfaceHeader interfaceHeader) {
        for (JrapInterfaceLine line : interfaceHeader.getLineList()) {
            line.setLineDescription(line.getLineName());
            if (line.getLineId() == null) {
                line.setHeaderId(interfaceHeader.getHeaderId());
                line.setLineId(UUID.randomUUID().toString());
                lineService.insertLine(iRequest, line);
            } else {
                lineService.updateLine(iRequest, line);
            }
        }
    }

    @Override
    public List<JrapInterfaceHeader> batchUpdate(IRequest request, List<JrapInterfaceHeader> interfaces) {
        for (JrapInterfaceHeader interfaceHeader : interfaces) {
            if (interfaceHeader.getHeaderId() == null) {
                self().createInterface(request, interfaceHeader);
            } else {
                self().updateInterface(request, interfaceHeader);
            }
        }
        return interfaces;
    }
}

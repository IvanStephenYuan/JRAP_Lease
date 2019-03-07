package com.jingrui.jrap.intergration.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import com.jingrui.jrap.intergration.dto.JrapInterfaceLine;
import com.jingrui.jrap.intergration.mapper.JrapInterfaceLineMapper;
import com.jingrui.jrap.intergration.service.IJrapInterfaceLineService;
import com.jingrui.jrap.cache.impl.ApiConfigCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 2016/7/26.
 *           xiangyu.qi on 2016/11/11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JrapInterfaceLineServiceImpl extends BaseServiceImpl<JrapInterfaceLine> implements IJrapInterfaceLineService {

    private final Logger logger = LoggerFactory.getLogger(JrapInterfaceLineServiceImpl.class);
    @Autowired
    private JrapInterfaceLineMapper hmsLineMapper;

    @Autowired
    private ApiConfigCache apiCache;

    @Override
    public List<JrapInterfaceLine> getLineAndLineTl(IRequest request, JrapInterfaceLine lineAndLineTlDTO) {
        return hmsLineMapper.getLineAndLineTl(lineAndLineTlDTO);
    }


    @Override
    public List<JrapInterfaceLine> getLinesByHeaderId(IRequest request,JrapInterfaceLine lineAndLineTlDTO,int page,int pagesize) {

        PageHelper.startPage(page, pagesize);
        List<JrapInterfaceLine> list = hmsLineMapper.getLinesByHeaderId(lineAndLineTlDTO);
        return list;
    }

    @Override
    public int insertLine(IRequest request, JrapInterfaceLine hmsInterfaceLine) {

        int result = hmsLineMapper.insertSelective(hmsInterfaceLine);

        if (result > 0) {
            apiCache.reload(hmsInterfaceLine.getLineId());
        }

        return result;
    }

    @Override
    public int updateLine(IRequest request, JrapInterfaceLine hmsInterfaceLine) {

        int result = hmsLineMapper.updateByPrimaryKeySelective(hmsInterfaceLine);
        checkOvn(result,hmsInterfaceLine);
        if (result > 0 ) {
            apiCache.reload();
        }

        return result;
    }

    @Override
    public int batchDelete(List<JrapInterfaceLine> list){
        int result = 0;
        for(JrapInterfaceLine line : list) {
            result = hmsLineMapper.deleteByPrimaryKey(line);
            checkOvn(result,line);
        }
        if(result >0) {
            apiCache.reload();
        }
        return result;
    }

    @Override
    public int batchDeleteByHeaders(IRequest request, List<JrapInterfaceHeader> lists) {

        int result = 0;

        for(JrapInterfaceHeader index : lists){
            JrapInterfaceLine line = new JrapInterfaceLine();
            line.setHeaderId(index.getHeaderId());

            hmsLineMapper.deleteTlByHeaderId(line);
            result = hmsLineMapper.deleteByHeaderId(line);
            checkOvn(result,line);
        }
        if(result >0) {
            apiCache.reload();
        }
        return result;
    }


}

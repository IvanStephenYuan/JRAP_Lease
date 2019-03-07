package com.jingrui.jrap.cache.impl;

import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import com.jingrui.jrap.intergration.mapper.JrapInterfaceHeaderMapper;
import com.jingrui.jrap.intergration.mapper.JrapInterfaceLineMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by user on 2016/8/1.
 */
public class ApiConfigCache<T>  extends HashStringRedisCache<JrapInterfaceHeader> {

    private static final Logger logger = LoggerFactory.getLogger(ApiConfigCache.class);
    private String apiSql = JrapInterfaceHeaderMapper.class.getName() + ".getAllHeaderAndLine";
    private String lineSql = JrapInterfaceLineMapper.class.getName() + ".getHeaderLineByLineId";

    {
        setLoadOnStartUp(true);
        setType(JrapInterfaceHeader.class);
    }

    @Override
    public JrapInterfaceHeader getValue(String key) {
        return super.getValue(key);
    }

    @Override
    public void setValue(String key, JrapInterfaceHeader headerAndLineDTO) {
        super.setValue(key, headerAndLineDTO);
    }


    public void initLoad() {

        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(apiSql, (resultContext) -> {
                JrapInterfaceHeader headerAndLineDTO = (JrapInterfaceHeader) resultContext.getResultObject();
                logger.info("cache result:{}", headerAndLineDTO.getInterfaceCode() + headerAndLineDTO.getLineCode());
                setValue(headerAndLineDTO.getInterfaceCode() + headerAndLineDTO.getLineCode(), headerAndLineDTO);
            });

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("init api cache error:", e);
            }
        }

    }


    public void reload(Object lineId) {
        logger.info("test  lineId:{}", lineId);
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            JrapInterfaceHeader headerAndLineDTO = sqlSession.selectOne(lineSql, lineId);
            if(headerAndLineDTO != null )
                setValue(headerAndLineDTO.getInterfaceCode() +JrapInterfaceHeader.CACHE_SEPARATOR+ headerAndLineDTO.getLineCode(), headerAndLineDTO);

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("reload api cache error:", e);
            }
        }


    }


}

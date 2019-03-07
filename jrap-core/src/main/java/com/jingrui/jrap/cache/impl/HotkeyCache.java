package com.jingrui.jrap.cache.impl;

import com.jingrui.jrap.core.ILanguageProvider;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.core.impl.ServiceRequest;
import com.jingrui.jrap.system.dto.Hotkey;
import com.jingrui.jrap.system.dto.Language;
import com.jingrui.jrap.system.mapper.HotkeyMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhizheng.yang@jingrui.com on 2017/10/17.
 */
public class HotkeyCache  extends HashStringRedisCache<Hotkey[]>{

    private Logger logger = LoggerFactory.getLogger(HotkeyCache.class);

    private String selectAll = HotkeyMapper.class.getName() + ".queryAll";
    private String selectByKey = HotkeyMapper.class.getName() + ".select";

    @Autowired
    private ILanguageProvider iLanguageProvider;

    {
        setLoadOnStartUp(true);
        setType(Hotkey[].class);
    }


    @Override
    public Hotkey[] getValue(String key) {
        return super.getValue(key);
    }

    @Override
    public void setValue(String key, Hotkey[] values){
        super.setValue(key,values);
    }


    public void load(String key) {
        super.remove(key);
        Map<String, List<Hotkey>> hotkeys = new HashMap<>();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(selectByKey, setHotkey(key), (resultContext) -> {
                Hotkey value = (Hotkey) resultContext.getResultObject();
                List<Hotkey> sets = hotkeys.get(key);
                if (sets == null) {
                    sets = new ArrayList<>();
                    hotkeys.put(key, sets);
                }
                sets.add(value);
            });

            hotkeys.forEach((k, v) -> {
                setValue(k, v.toArray(new Hotkey[v.size()]));
            });
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("load hotkey cache exception: ", e);
            }
        }
    }

    @Override
    protected void initLoad() {
        Map<String, List<Hotkey>> hotkeys = new HashMap<>();
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(selectAll, (resultContext) -> {
                Hotkey value = (Hotkey) resultContext.getResultObject();
                String key = value.getHotkeyLevel() + "_" + value.getHotkeyLevelId().toString();
                List<Hotkey> items = hotkeys.get(key);
                if (items == null) {
                    items = new ArrayList<>();
                    hotkeys.put(key, items);
                }
                items.add(value);
            });

            hotkeys.forEach((k, v) -> {
                setValue(k, v.toArray(new Hotkey[v.size()]));
            });
            if (logger.isDebugEnabled()) {
                logger.debug("successfully loaded all hotkey cache");
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("init hotkey cache exception: ", e);
            }
        }
    }

    private Hotkey setHotkey(String key){
        Hotkey hotkey = new Hotkey();
        if(key!= null && key.indexOf("_") !=  -1){
            hotkey.setHotkeyLevelId(Long.parseLong(StringUtils.substringAfterLast(key, "_")));
            hotkey.setHotkeyLevel(StringUtils.substringBeforeLast(key, "_"));
        }
        return hotkey;
    }





}

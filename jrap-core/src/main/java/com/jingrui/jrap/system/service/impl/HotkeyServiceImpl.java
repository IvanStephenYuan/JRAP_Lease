package com.jingrui.jrap.system.service.impl;

import com.jingrui.jrap.cache.impl.HotkeyCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.dto.Hotkey;
import com.jingrui.jrap.system.service.IHotkeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Transactional(rollbackFor = Exception.class)
public class HotkeyServiceImpl extends BaseServiceImpl<Hotkey> implements IHotkeyService {

    @Autowired
    private HotkeyCache hotkeyCache;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Hotkey insertSelective(IRequest request,@StdWho Hotkey hotkey) {
        if (hotkey == null) {
            return null;
        }
        Hotkey hotkey1 = super.insertSelective(request, hotkey);
        hotkeyCache.load(hotkey1.getHotkeyLevel() + "_" + hotkey1.getHotkeyLevelId());
        return hotkey1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Hotkey updateByPrimaryKeySelective(IRequest request,@StdWho Hotkey hotkey) {
        if (hotkey == null) {
            return null;
        }
        Hotkey result = super.updateByPrimaryKeySelective(request, hotkey);
        hotkeyCache.load(result.getHotkeyLevel() + "_" + result.getHotkeyLevelId());
        return result;
    }

    @Override
    public int batchDelete(List<Hotkey> hotkeys) {
        int c = 0;
        String key = hotkeys.get(0).getHotkeyLevel() + "_" + hotkeys.get(0).getHotkeyLevelId();
        for (Hotkey hotkey : hotkeys) {
            c += self().deleteByPrimaryKey(hotkey);
        }
        hotkeyCache.load(key);
        return c;
    }


    @Override
    public List<Hotkey> preferenceQuery(IRequest iRequest) {
        Hotkey hotkeyS = new Hotkey();
        hotkeyS.setHotkeyLevel("system");
        hotkeyS.setHotkeyLevelId((long) 0);
        List<Hotkey> hotkeySys = super.selectOptions(iRequest, hotkeyS, null);
        Hotkey hotkeyU = new Hotkey();
        hotkeyU.setHotkeyLevel("user");
        hotkeyU.setHotkeyLevelId(iRequest.getUserId());
        List<Hotkey> hotkeyUser = super.selectOptions(iRequest, hotkeyU, null);
        Map<String, Hotkey> hotkeys = new TreeMap<>();
        if (hotkeySys != null) {
            for (Hotkey hotkey : hotkeySys) {
                hotkeys.put(hotkey.getCode(), hotkey);
            }
        }
        if (hotkeyUser != null) {
            for (Hotkey hotkey : hotkeyUser) {
                Hotkey value = hotkeys.get(hotkey.getCode());
                if (value != null) {
                    hotkey.setDescription(value.getDescription());
                    hotkeys.put(hotkey.getCode(), hotkey);
                }
            }
        }
        List<Hotkey> datas = new ArrayList<>();
        datas.addAll(hotkeys.values());
        return datas;
    }

}
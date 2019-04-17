package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.item.mapper.ItemMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.item.dto.Item;
import com.jingrui.jrap.item.service.IItemService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemServiceImpl extends BaseServiceImpl<Item> implements IItemService {
    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}
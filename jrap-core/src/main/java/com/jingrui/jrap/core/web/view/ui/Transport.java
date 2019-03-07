package com.jingrui.jrap.core.web.view.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * dataSource-TransPort
 * 
 * @author hailin.xu@jingrui.com
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transport {

    private TransportMethod read;
    private TransportMethod create;
    private TransportMethod update;
    private TransportMethod destroy;
    private ReferenceType parameterMap;
    private ReferenceType push;

    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "schema");
        if (getRead() != null) {
            map.addChild(getRead().toXMap("read"));
        }
        if (getCreate() != null) {
            map.addChild(getCreate().toXMap("create"));
        }
        if (getDestroy() != null) {
            map.addChild(getDestroy().toXMap("destroy"));
        }
        if (getUpdate() != null) {
            map.addChild(getUpdate().toXMap("update"));
        }
        map.put("parameterMap", getParameterMap());
        map.put("push", getPush());
        return map;
    }

    public TransportMethod getRead() {
        return read;
    }

    public void setRead(TransportMethod read) {
        this.read = read;
    }

    public TransportMethod getCreate() {
        return create;
    }

    public void setCreate(TransportMethod create) {
        this.create = create;
    }

    public TransportMethod getUpdate() {
        return update;
    }

    public void setUpdate(TransportMethod update) {
        this.update = update;
    }

    public TransportMethod getDestroy() {
        return destroy;
    }

    public void setDestroy(TransportMethod destroy) {
        this.destroy = destroy;
    }

    public ReferenceType getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(ReferenceType parameterMap) {
        this.parameterMap = parameterMap;
    }

    public ReferenceType getPush() {
        return push;
    }

    public void setPush(ReferenceType push) {
        this.push = push;
    }
}

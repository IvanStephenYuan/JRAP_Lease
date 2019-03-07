package com.jingrui.jrap.core.web.view.xpath;

import com.jingrui.jrap.core.web.view.XMap;

public interface Predicate {
    public boolean validate(XMap node);
}

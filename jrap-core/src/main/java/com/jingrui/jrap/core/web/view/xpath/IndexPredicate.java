package com.jingrui.jrap.core.web.view.xpath;

import com.jingrui.jrap.core.web.view.XMap;

public class IndexPredicate implements Predicate {

    private int index;

    public IndexPredicate(int index) {
        this.index = index;
    }

    @Override
    public boolean validate(XMap node) {
        XMap parent = node.getParent();
        if (parent != null) {
            return (parent.getChildren().indexOf(node)+1) == index;
        }
        return false;
    }

}

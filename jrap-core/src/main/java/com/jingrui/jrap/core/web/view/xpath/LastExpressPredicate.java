package com.jingrui.jrap.core.web.view.xpath;

import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.base.Splitter;
import com.jingrui.jrap.core.web.view.XMap;

public class LastExpressPredicate implements Predicate {
    
    private static final Splitter MINUS_SPLITTER = Splitter.on("-").trimResults().omitEmptyStrings();

    private int decrease;
    
    public LastExpressPredicate(String value){
        Iterable<String> attributes = MINUS_SPLITTER.split(value);
        for (String att : attributes) {
            if(NumberUtils.isNumber(att)){
                decrease = NumberUtils.toInt(att);
            }
        }
    }
    
    
    @Override
    public boolean validate(XMap node) {
        XMap parent = node.getParent();
        if (parent != null) {
            int index = parent.getChildren().size()-decrease-1;
            if(index>=0)
            return parent.getChildren().get(index) == node;
        }
        return false;
    }

}

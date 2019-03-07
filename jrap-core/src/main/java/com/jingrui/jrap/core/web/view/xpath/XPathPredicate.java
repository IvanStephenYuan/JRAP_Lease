package com.jingrui.jrap.core.web.view.xpath;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class XPathPredicate {
    
    
    private XPathPredicate(){}
    
    
    public static Predicate build(String value){
        Predicate predicate = null;
        //[@xxx]
        if(value.startsWith("[@")){
            predicate = new EqualPredicate(value.substring(2, value.length() - 1));
        }else{
            value =  value.substring(1, value.length() - 1);
            //[1]
            if(NumberUtils.isNumber(value)){
                predicate = new IndexPredicate(NumberUtils.toInt(value));
            }else if(StringUtils.equalsIgnoreCase(value, "last()")){
                predicate = new LastPredicate();
            }else if(StringUtils.containsIgnoreCase(value, "last()")){
                predicate = new LastExpressPredicate(value);
            }
        }
        return predicate;
    }
}

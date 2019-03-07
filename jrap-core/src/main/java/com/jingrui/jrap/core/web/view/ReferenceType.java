package com.jingrui.jrap.core.web.view;

import org.apache.commons.lang.StringUtils;

import com.jingrui.jrap.core.BaseConstants;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class ReferenceType {
    public String reference;
    
    public ReferenceType(String reference) {
        this.reference = reference;
        if(StringUtils.startsWith(reference, BaseConstants.XML_DATA_TYPE_FUNCTION)) {
            this.reference = StringUtils.substringAfter(reference, BaseConstants.XML_DATA_TYPE_FUNCTION);
        }
    }

    public String getReference() {
        return reference;
    }
    
    public static ReferenceType create(String reference) {
        if(reference!=null){
            return new ReferenceType(reference);
        }
        return null;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}

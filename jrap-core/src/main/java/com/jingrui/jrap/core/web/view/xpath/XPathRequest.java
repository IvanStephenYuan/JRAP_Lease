package com.jingrui.jrap.core.web.view.xpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jingrui.jrap.core.web.view.XMap;


/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class XPathRequest {
    
    private static final String SLASH = "/";
    
    private Map<String,String> namespaceMapping = new HashMap<>();

    private List<XPathStep> stepList = new ArrayList<>();
    
    public XPathRequest(String request, Map<String,String> mapping) {
        if(mapping!=null) namespaceMapping = mapping;
        String[] steps = request.split(SLASH);
        for(String step:steps){
            stepList.add(new XPathStep(step, this));
        }
    }
    
    
    public String getNamespace(String prefix){
        return namespaceMapping.get(prefix);
    }
    
    public boolean validate(XMap node){
        int len = stepList.size();
        if(len>0){
            return validateStep(node,len-1);
        }
        return false;
    }
    
    public boolean validateStep(XMap node, int index){
        if(index<0) return true;
        XPathStep step = stepList.get(index);
        if("*".equals(step.getNodeName())){
            index = index-1;
            step = stepList.get(index);
            node = findMatchParentNode(node, step);
        }
        if(step.validate(node)){
            return node == null || validateStep(node.getParent(), index - 1);
        }
        return false;
    }
    
    
    public XMap findMatchParentNode(XMap node, XPathStep step){
        XMap result = null;
        while(node!=null){
            if(node.getName().equals(step.getNodeName())){
                result = node;
                break;
            }
            node = node.getParent();
        }
        return result;
    }
    
}

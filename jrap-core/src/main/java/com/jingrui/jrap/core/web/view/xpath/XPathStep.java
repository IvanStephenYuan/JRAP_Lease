package com.jingrui.jrap.core.web.view.xpath;

import org.apache.commons.lang3.StringUtils;

import com.jingrui.jrap.core.web.view.XMap;

public class XPathStep {

    private static final String COLON = ":";
    private XPathRequest request;
    private String nodeName;
    private String namespce;
    private Predicate predicate;

    public String getNodeName() {
        return nodeName;
    }

    public XPathStep(String value, XPathRequest request) {
        this.request = request;
        int elementEnd = getElementEnd(value);
        this.parseNodeName(value.substring(0, elementEnd));
        if (elementEnd != value.length()) {
            predicate = XPathPredicate.build(value.substring(elementEnd));
        }
    }

    private void parseNodeName(String name) {
        if (StringUtils.contains(name, COLON)) {
            this.nodeName = StringUtils.substringAfter(name, COLON);
            String prefix = StringUtils.substringBefore(name, COLON);
            this.namespce = this.request.getNamespace(prefix);
        } else {
            this.nodeName = name;
        }
    }

    private int getElementEnd(String value) {
        int bIndex = value.indexOf('[');
        return bIndex > 0 ? bIndex : value.length();
    }

    public boolean validate(XMap node) {
        return validateName(node) && validatePredicate(node);
    }

    /**
     * (1)根节点 (2)任意节点 (3)匹配名字的节点
     * 
     * @param node
     * @return
     */
    private boolean validateName(XMap node) {
        return (node == null && StringUtils.isEmpty(this.nodeName)) || (node.getName().equals(this.nodeName) && node.getNamespaceURI().equals(this.namespce));
    }

    private boolean validatePredicate(XMap node) {
        if (this.predicate != null && node != null) {
            return this.predicate.validate(node);
        }
        return true;
    }
}

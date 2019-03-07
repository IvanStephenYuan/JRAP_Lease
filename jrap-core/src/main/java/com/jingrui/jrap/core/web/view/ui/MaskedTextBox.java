package com.jingrui.jrap.core.web.view.ui;

import java.util.HashMap;
import java.util.Map;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 文本框.
 * 
 * @author hailin.xu@jingrui.com
 * @author njq.niu@jingrui.com
 * 
 * 
 */
@UITag
public class MaskedTextBox extends InputField {

    public static final String PROPERTITY_CLEAR_PROMPT_CHAR = "clearPromptChar";
    public static final String PROPERTITY_MASK = "mask";
    public static final String PROPERTITY_PROMPT_CHAR = "promptChar";
    public static final String PROPERTITY_RULES = "rules";
    public static final String PROPERTITY_UNMASK_ON_POST = "unmaskOnPost";
    public static final String PROPERTITY_CASE_LETTER = "caseLetter";

    public static MaskedTextBox createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "maskedTextBox");
        MaskedTextBox instance = new MaskedTextBox();
        instance.initPrototype(view);
        return instance;
    }

    public Boolean getClearPromptChar() {
        return getPrototype().getBoolean(PROPERTITY_CLEAR_PROMPT_CHAR);
    }

    public void setClearPromptChar(Boolean clearPromptChar) {
        setPropertity(PROPERTITY_CLEAR_PROMPT_CHAR, clearPromptChar);
    }

    public String getMask() {
        return getPrototype().getString(PROPERTITY_MASK);
    }

    public void setMask(String mask) {
        setPropertity(PROPERTITY_MASK, mask);
    }

    public String getPromptChar() {
        return getPrototype().getString(PROPERTITY_PROMPT_CHAR);
    }

    public void setPromptChar(String promptChar) {
        setPropertity(PROPERTITY_PROMPT_CHAR, promptChar);
    }

    public Map<String, ReferenceType> getRules() {
        Map<String, ReferenceType> rules = new HashMap<>();
        XMap children = getPrototype().getChild(PROPERTITY_RULES);
        if (children != null) {
            children.getChildren(true).forEach(item -> {
                rules.put(item.getString("name"), new ReferenceType(item.getString("value")));
            });
        }
        return rules;
    }

    public void addRule(String name, String rule) {
        XMap children = getPrototype().getChild(PROPERTITY_RULES);
        if (children != null) {
            children = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "rules");
            getPrototype().addChild(children);
        }
        XMap child = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "rule");
        child.put(name, rule);
        children.addChild(child);
    }

    public Boolean getUnmaskOnPost() {
        return getPrototype().getBoolean(PROPERTITY_UNMASK_ON_POST);
    }

    public void setUnmaskOnPost(Boolean unmaskOnPost) {
        setPropertity(PROPERTITY_UNMASK_ON_POST, unmaskOnPost);
    }

    public String getCaseLetter() {
        return getPrototype().getString(PROPERTITY_CASE_LETTER);
    }

    public void setCaseLetter(String caseLetter) {
        setPropertity(PROPERTITY_CASE_LETTER, caseLetter);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_CLEAR_PROMPT_CHAR, getClearPromptChar());
        context.addJsonConfig(PROPERTITY_MASK, getMask());
        context.addJsonConfig(PROPERTITY_PROMPT_CHAR, getPromptChar());
        context.addJsonConfig(PROPERTITY_RULES, getRules());
        context.addJsonConfig(PROPERTITY_UNMASK_ON_POST, getUnmaskOnPost());
        context.addJsonConfig(PROPERTITY_CASE_LETTER, getCaseLetter());
    }
}

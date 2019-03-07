package com.jingrui.jrap.core.web.view.xpath;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.util.Iterator;

import com.google.common.base.Splitter;
import com.jingrui.jrap.core.web.view.XMap;

public class EqualPredicate implements Predicate {

    private static final String EQUALS = "=";
    private static final Splitter EQUALS_SPLITTER = Splitter.on(EQUALS).trimResults().omitEmptyStrings();
    private static final Splitter SPACE_SPLITTER = Splitter.on(" ").trimResults().omitEmptyStrings();
    private String attributeName;
    private String attributeValue;
    
    
    public EqualPredicate(String value){
        Iterable<String> attributes = SPACE_SPLITTER.split(value);
        for (String att : attributes) {
            Iterator<String> iter = splitByEquals(att);
            addPredicate(iter.next(), iter.hasNext() ? iter.next() : "");
        }
    }
    
    private static Iterator<String> splitByEquals(String value) {
        Iterator<String> iter = EQUALS_SPLITTER.split(value).iterator();
        checkArgument(iter.hasNext(), "unable to parse attribute " + value);
        return iter;
    }
    
    @Override
    public boolean validate(XMap node) {
        String v = node.getString(attributeName);
        return v == null ? false : (v.equals(attributeValue));
    }
    
    private void addPredicate(String name, String value) {
        checkArgument(!name.contains(EQUALS));
        attributeName = name;
        attributeValue = buildValue(value);
    }
    
    private static String buildValue(String value) {
        return checkNotNull(value).replaceAll("'", "").replaceAll("\"", "");
    }

}

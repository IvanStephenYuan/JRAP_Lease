package com.jingrui.jrap.core.web.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

import com.jingrui.jrap.core.web.view.XMapUtil.XPathFinder;
import com.jingrui.jrap.core.web.view.ui.ViewTag;
import com.jingrui.jrap.core.web.view.xpath.XPathRequest;


/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class XMapUtil {

    public static final String ANY_VALUE = "*";
    public static final String NULL_VALUE = "null";
    
    public static final String MERGE_ACTION = "action";
    public static final String MERGE_ACTION_AFTER = "after";
    public static final String MERGE_ACTION_BEFORE = "before";
    public static final String MERGE_ACTION_REPLACE = "replace";
    public static final String MERGE_ACTION_REMOVE = "remove";
    public static final String MERGE_ACTION_ATTRIBUTE = "attribute";
    
    
    public static class XPathFinder implements IterationHandle<XMap> {

        private XPathRequest request;
        private XMap result;

        public XPathFinder(XPathRequest request) {
            this.request = request;
        }

        @Override
        public int process(XMap node) {
            if (request.validate(node)) {
                result = node;
                return IterationHandle.IT_BREAK;
            }
            return IterationHandle.IT_CONTINUE;
        }

        private XMap getResult() {
            return result;
        }

        public XMap find(XMap node) {
            if (node != null) {
                node.getRoot().iterate(this, true);
            }
            return this.getResult();
        }

    }

    public static class NameFinder implements IterationHandle<List<XMap>> {

        private String nodeName;

        private List<XMap> result = new ArrayList<>();

        public List<XMap> find(XMap node) {
            if (node != null) {
                node.getRoot().iterate(this, true);
            }
            return this.getResult();
        }
        
        private List<XMap> getResult() {
            return result;
        }

        public int process(XMap map) {
            if (StringUtils.equals(nodeName, map.getName())) {
                result.add(map);
            }
            return IterationHandle.IT_CONTINUE;
        }

        public NameFinder(String nodeName) {
            this.nodeName = nodeName;
        }
    }

    public static List<XMap> findByName(XMap root, String nodeName) {
        if (root == null)
            return null;
        NameFinder cf = new NameFinder(nodeName);
        return cf.find(root);
    }

    public static void merge(XMap source, XMap dest) {
        List<XMap> list =  findByName(dest,"xpath");
        for(XMap xpath:list){
            String action = xpath.getString(MERGE_ACTION);
            String express = xpath.getString("expr");
            XPathRequest request = new XPathRequest(express, xpath.getNamespaceMapping());
            XPathFinder cf = new XPathFinder(request);
            XMap result = cf.find(source);
            if(result!=null){
                AtomicInteger index = new AtomicInteger(result.getParent().getChildren().indexOf(result));
                if(MERGE_ACTION_REMOVE.equalsIgnoreCase(action)){
                    result.getParent().removeChild(result);
                }else if (MERGE_ACTION_ATTRIBUTE.equalsIgnoreCase(action)){
                    xpath.getChildren().forEach(child->{
                        result.put(child.getString("name"), child.getString("value"));
                    });
                }else if (MERGE_ACTION_REPLACE.equalsIgnoreCase(action)){
                    result.getParent().remove(result);
                    xpath.getChildren().forEach(child->{
                        result.getParent().addChild(index.incrementAndGet(), child);
                    });
                }else {
                    xpath.getChildren().forEach(child->{
                        if(MERGE_ACTION_AFTER.equalsIgnoreCase(action)) {
                            result.getParent().addChild(index.incrementAndGet(), child);
                        }else if (MERGE_ACTION_BEFORE.equalsIgnoreCase(action)){
                            int i = index.get();
                            result.getParent().addChild(i >= 0 ? i : 0, child);
                        }
                    });
                }
            }
        }
    }

    public static boolean compare(Object field, String value) {
        if (field == null) {
            if (value == null)
                return true;
            return NULL_VALUE.equals(value);
        } else {
            if (ANY_VALUE.equals(value))
                return true;
            else
                return field.toString().equals(value);
        }
    }

    /**
     * Find parent with specified name
     * 
     * @param map
     * @param element_name
     * @return
     */
    public static XMap findParentWithName(XMap map, String element_name) {
        XMap parent = map.getParent();
        while (parent != null) {
            if (element_name != null)
                if (element_name.equals(parent.getName()))
                    return parent;
            parent = parent.getParent();
        }
        return null;
    }

    private static String getKey(XMap map, Object[] keys) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < keys.length; i++)
            buf.append(map.get(keys[i]));
        return buf.toString();
    }

    /**
     * Join to list of XMap, put all fields in list2 into the one in list1 that
     * has the same value get from each element of key_fields
     * 
     * @param list1
     *            The target list to be joined
     * @param list2
     *            The source list whose items will be examined and joined into
     *            list1
     * @param key_fields
     *            An array of key
     */
    public static void join(Collection list1, Collection list2, Object[] key_fields) {
        HashMap join_index = new HashMap();
        Iterator it = list1.iterator();
        while (it.hasNext()) {
            XMap item = (XMap) it.next();
            String key = getKey(item, key_fields);
            join_index.put(key, item);
        }
        it = list2.iterator();
        while (it.hasNext()) {
            XMap source = (XMap) it.next();
            String key = getKey(source, key_fields);
            XMap target = (XMap) join_index.get(key);
            if (target != null)
                target.putAll(source);
        }
        join_index.clear();
    }

    /**
     * Make a XMap simple <root> <field1>1</field1> -> <root field1="1"/>
     * </root>
     */
    public static XMap collapse(XMap root) {
        List childs = root.getChildren();
        if (childs == null)
            return root;
        ListIterator it = childs.listIterator();
        while (it.hasNext()) {
            XMap child = (XMap) it.next();
            String text = child.getText();
            if (child.size() == 0 && text != null && !"".equals(text.trim())) {
                root.put(child.getName(), text);
                it.remove();
            } else
                collapse(child);
        }
        return root;
    }

    public static XMap expand(XMap root) {

        List childs = root.getChildren();
        if (childs != null) {
            ListIterator it = childs.listIterator();
            while (it.hasNext()) {
                XMap child = (XMap) it.next();

                expand(child);
            }
        }
        Set keyset = root.keySet();
        Iterator it = keyset.iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            XMap keychild = new XMap(key);
            keychild.setText(root.getString(key));
            root.addChild(keychild);
        }
        HashSet hs = new HashSet();
        hs.addAll(keyset);
        Iterator its = hs.iterator();
        while (its.hasNext()) {
            Object key = its.next();
            root.remove(key);
        }

        return root;

    }

    /**
     * put childs into a Map, using specified field as key in each child item
     * 
     * @param target
     *            Target Map to hold data
     * @param data
     *            Source data containing childs that will be processed
     * @param key_field
     *            key field that will identify each child
     */
    public static void fillMap(Map target, XMap data, Object key_field) {
        Iterator it = data.getChildIterator();
        if (it == null)
            return;
        while (it.hasNext()) {
            XMap item = (XMap) it.next();
            Object key = item.get(key_field);
            target.put(key, item);
        }
    }

    /**
     * put specified field value into a Map
     * 
     * @param target
     *            Target Map to hold data
     * @param data
     *            Source data containing childs that will be processed
     * @param key_field
     *            key field that will identify each child
     * @param value_field
     *            value field that will be put into target Map
     */
    public static void fillMap(Map target, XMap data, Object key_field, Object value_field) {
        Iterator it = data.getChildIterator();
        if (it == null)
            return;
        while (it.hasNext()) {
            XMap item = (XMap) it.next();
            Object key = item.get(key_field);
            Object value = item.get(value_field);
            target.put(key, value);
        }
    }

    /**
     * connect attribute from all childs into a string, separated by specified
     * separator string
     */
    public static String connectAttribute(XMap root, String attrib_name, String separator) {
        if (root == null)
            return null;
        Iterator it = root.getChildIterator();
        if (it == null)
            return null;
        StringBuffer result = new StringBuffer();
        int id = 0;
        while (it.hasNext()) {
            XMap child = (XMap) it.next();
            Object value = child.get(attrib_name);
            if (id > 0)
                result.append(separator);
            result.append(value);
            id++;
        }
        return result.toString();
    }

    public static String connectAttribute(XMap root, String attrib_name) {
        return connectAttribute(root, attrib_name, ",");
    }

    /**
     * Copy all attributes, from source map to destination map. Do not override
     * existing attribute.
     */
    public static void copyAttributes(Map source, Map dest) {
        Iterator it = source.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (!dest.containsKey(entry.getKey()))
                dest.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Merge childs from source to destination. Every child is identified by
     * specified key attribute Copy all childs from source.If a childs exists in
     * destination map, all attributes that not defined in dest map will be
     * copied.
     * 
     * @param source
     *            Source XMap containing childs
     * @param dest
     *            Desitination XMap containg childs
     * @param key
     *            Key attribute to identify each child XMap item, such as
     *            "name", "id"
     */
    public static void mergeChildsByOverride(XMap source, XMap dest, Object key) {
        // Map source_cache = new HashMap();
        // fillMap( source_cache, source, key);
        Iterator it = source.getChildIterator();
        if (it == null)
            return;
        Map dest_cache = new HashMap();
        fillMap(dest_cache, dest, key);
        while (it.hasNext()) {
            XMap source_item = (XMap) it.next();
            Object value = source_item.get(key);
            XMap dest_item = (XMap) dest_cache.get(value);
            if (dest_item != null)
                copyAttributes(source_item, dest_item);
            else
                dest.addChild((XMap) source_item.clone());
        }
        dest_cache.clear();
    }

    /**
     * For each child item in destination XMap, if a child item with same key
     * exists in source XMap, then copy all attributes that not defined in
     * destination map from source.
     * 
     * @param source
     *            Source XMap containing childs
     * @param dest
     *            Desitination XMap containg childs
     * @param key
     *            Key attribute to identify each child XMap item, such as
     *            "name", "id"
     */
    public static void mergeChildsByReference(XMap source, XMap dest, Object key) {
        Iterator it = dest.getChildIterator();
        if (it == null)
            return;
        Map source_cache = new HashMap();
        fillMap(source_cache, source, key);
        while (it.hasNext()) {
            XMap dest_item = (XMap) it.next();
            Object value = dest_item.get(key);
            XMap source_item = (XMap) source_cache.get(value);
            if (source_item != null)
                copyAttributes(source_item, dest_item);
        }
        source_cache.clear();
    }

    public static void getLevelChilds(XMap source, int level, List result) {
        if (source == null)
            return;
        if (level == 0)
            result.add(source);
        if (source.getChildren() == null)
            return;
        if (level == 1) {
            result.addAll(source.getChildren());
        } else if (level > 1) {
            for (Iterator it = source.getChildIterator(); it.hasNext();) {
                getLevelChilds((XMap) it.next(), level - 1, result);
            }
        }
    }

}

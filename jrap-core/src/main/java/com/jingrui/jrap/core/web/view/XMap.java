package com.jingrui.jrap.core.web.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.web.view.XMapUtil.XPathFinder;
import com.jingrui.jrap.core.web.view.ui.ViewTag;
import com.jingrui.jrap.core.web.view.xpath.XPathRequest;

public class XMap extends HashMap<Object, Object> implements Cloneable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_INITIAL_CAPACITY = 50;
    
    

    protected String name;
    protected String prefix = ViewTag.DEFAULT_TAG_PREFIX;
    protected String namespaceURI = ViewTag.DEFAULT_NAME_SPACE;
    protected String text;
    
    protected XMap parent;
    protected List<XMap> children;
    protected Map<String,String>   namespaceMapping;

    public XMap() {
        super(DEFAULT_INITIAL_CAPACITY);
    }

    public XMap(String name) {
        super(DEFAULT_INITIAL_CAPACITY);
        setName(name);
    }

    public XMap(String name, String namespaceURI) {
        super(DEFAULT_INITIAL_CAPACITY);
        setName(name);
        setNameSpaceURI(namespaceURI);
    }

    public XMap(String prefix, String namespaceURI, String name) {
        super(DEFAULT_INITIAL_CAPACITY);
        setName(name);
        setPrefix(prefix);
        setNameSpaceURI(namespaceURI);
    }

    public XMap(XMap another) {
        this(another.prefix, another.namespaceURI, another.name);
        copy(another);
    }

    public XMap(String name, Map<Object, Object> map) {
        super();
        setName(name);
        putAll(map);
    }

    public void addChild(Collection<XMap> children) {
        if (children != null)
        children.forEach(this::addChild);
    }

    public XMap replaceChild(XMap child, XMap newChild) {
        if (child == null)
            return null;
        ListIterator<XMap> it = (ListIterator<XMap>) getChildIterator();
        if (it == null)
            return null;
        while (it.hasNext()) {
            XMap m = (XMap) it.next();
            if (m == child) {
                if (newChild == null) {
                    it.remove();
                    return null;
                }
                newChild.setParent(this);
                it.set(newChild);
                return newChild;
            }
        }
        return null;
    }


    public XMap replaceChild(String childName, XMap newChild) {
        return replaceChild(getChild(childName), newChild);
    }

    public XMap copy(XMap another) {
        // clear();
        putAll(another);
        addChild(another.children);
        this.text = another.text;
        return this;
    }

    /**
     * set then name of this CompositeMap
     * 
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set namespace uri
     * 
     * @param uri
     *            new namespace uri
     */
    public void setNameSpaceURI(String uri) {
        this.namespaceURI = uri;
    }

    /**
     * set prefix string.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * set namespace uri and prefix.
     */
    public void setNameSpace(String prefix, String uri) {
        setPrefix(prefix);
        setNameSpaceURI(uri);
    }

    /**
     * get name of this XMap
     */
    public String getName() {
        return name;
    }

    /**
     * get prefix string
     * 
     * @return prefix of this XMap
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * get namespace uri
     * 
     * @return namespace uri of this XMap
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }

    /**
     * get raw name, which is equals to prefix+":"+name
     * 
     * @return raw name, if prefix is null this is equals to name
     */
    public String getRawName() {
        if (prefix == null)
            return name;
        else
            return prefix + ':' + name;
    }

    /** gets text ( CDATA section in XML document ) */
    public String getText() {
        return text;
    }

    /** sets text ( CDATA section in XML document ) */
    public void setText(String t) {
        text = t;
    }

    public void setParent(XMap p) {
        parent = p;
    }

    public XMap getParent() {
        return parent;
    }

    public XMap getRoot() {
        XMap map = parent;
        while (map != null) {
            if (map.getParent() == null) {
                return map;
            } else {
                map = map.getParent();
            }
        }
        return this;
    }

    public Object put(Object key, Object value) {
        if (value != null)
            if (value instanceof XMap) {
                ((XMap) value).setParent(this);
            }
        /*
         * if( key != null && key instanceof String) key =
         * ((String)key).toLowerCase();
         */
        return super.put(key, value);
    }

    /*
     * 
     * public Object get( Object key){ if( key != null && key instanceof String)
     * key = ((String)key).toLowerCase(); return super.get(key); }
     */

    public Object putObject(String key, Object value, char attribute_char) {
        if (key == null)
            return null;
        if (key.charAt(0) == attribute_char)
            return put(key.substring(1), value);
        else {
            if (value instanceof XMap && value != null) {
                XMap cmap = (XMap) value;
                cmap.setName(key);
                addChild(cmap);
                return value;
            } else
                return null;
        }
    }


    Object getObject(String key, char attribute_char) {
        if (key == null)
            return null;
        if (key.charAt(0) == attribute_char)
            return get(key.substring(1));
        else
            return getChild(key);
    }

    public void addChild(int index, XMap child) {
        child.parent = this;
        getChildren(true).add(index, child);
    }

    /**
     * add an XMap instance to child list
     * 
     * @param child
     *            child XMap to add
     */
    public void addChild(XMap child) {
        child.parent = this;
        getChildren(true).add(child);
    }

    /**
     * Remove a child XMap from children list
     * 
     * @param child
     *            Child XMap to remove
     * @return true if remove is success
     */
    public boolean removeChild(XMap child) {
        boolean removed = false;
        if (children != null) {
            ListIterator<XMap> it = children.listIterator();
            while (it.hasNext()) {
                XMap obj = it.next();
                if (obj == child) {
                    it.remove();
                    removed = true;
                    break;
                }
            }
            if (removed)
                child.setParent(null);
        }
        return removed;
    }

    /**
     * create an child XMap with specified name and namespace
     * 
     * @param prefix
     *            prefix string
     * @param uri
     *            namespace uri
     * @param name
     *            name of child
     * @return new child XMap
     * @see XMap(String, String,String)
     */
    public XMap createChild(String prefix, String uri, String name) {
        XMap child = new XMap(prefix, uri, name);
        addChild(child);
        return child;
    }

    public XMap createChild(String name) {
        return createChild(null, null, name);
    }

    /**
     * get an child XMap which is euqal with parameter
     * 
     * @param child
     *            XMap to compare
     * @return child XMap found or null if not found
     */
    public XMap getChild(XMap child) {
        if (children == null)
            return null;

        Iterator<XMap> it = children.iterator();
        while (it.hasNext()) {
            XMap node = (XMap) it.next();
            if (node.equals(child))
                return node;
        }

        return null;
    }

    /**
     * get a child XMap with specified name
     * 
     * @param name
     *            name of XMap to find
     * @return child XMap found or null
     */
    public XMap getChild(String name) {
        if (this.children == null)
            return null;
        
        Iterator<XMap> it = children.iterator();
        while (it.hasNext()) {
            XMap node = (XMap) it.next();
            String nm = node.getName();
            if (nm != null)
                if (nm.equals(name))
                    return node;
        }
        return null;
    }


    public List<XMap> getChildren() {
        return children;
    }

    public List<XMap> getChildren(boolean create) {
        if(create && this.children == null)
            this.children = new LinkedList<XMap>();
        return this.children;
    }

    public Iterator<XMap> getChildIterator() {
        if (children == null)
            return null;
        return children.iterator();
    }

    public String toXML() {
        return XMLOutputter.defaultInstance().toXML(this);
    }

    /**
     * @see java.util.Map#clear()
     */
    public void clear() {
        super.clear();
        if (children != null) {
            children.clear();
        }
        name = null;
        text = null;
        namespaceURI = null;
        prefix = null;
        children = null;
        parent = null;
    }

    /**
     * create a new XMap and copy content to it
     */
    public Object clone() {
        XMap m = (XMap) super.clone();
        if (children != null) {
            m.children = new LinkedList<XMap>();
            Iterator<XMap> it = children.iterator();
            while (it.hasNext()) {
                XMap child = it.next();
                XMap new_child = (XMap) child.clone();
                new_child.setParent(m);
                m.children.add(new_child);
            }
        }
        return m;
    }
    
    /**
     * 根据值前缀区分类型，默认返回字符串.
     * <p>
     *  <ul>
     *      <li>fn:方法，返回ReferenceType
     *      <li>integer:方法，返回Integer
     *      <li>double:方法，返回Double
     *      <li>float:方法，返回Float
     *      <li>boolean:方法，返回Boolean
     *      <li>long:方法，返回Long
     *      <li>date:方法，返回Date
     *  </ul>
     * </p>
     * 
     * @param key
     * @return
     */
    public Object getPropertity(String key){
        String value = getString(key);
        if(StringUtils.startsWith(value, BaseConstants.XML_DATA_TYPE_FUNCTION)) {
            return new ReferenceType(StringUtils.substringAfter(value, BaseConstants.XML_DATA_TYPE_FUNCTION));
        } else if(StringUtils.startsWith(value, BaseConstants.XML_DATA_TYPE_BOOLEAN)) {
            return new Boolean(StringUtils.substringAfter(value, BaseConstants.XML_DATA_TYPE_BOOLEAN));
        } else if(StringUtils.startsWith(value, BaseConstants.XML_DATA_TYPE_INTEGER)) {
            return new Integer(StringUtils.substringAfter(value, BaseConstants.XML_DATA_TYPE_INTEGER));
        } else if(StringUtils.startsWith(value, BaseConstants.XML_DATA_TYPE_LONG)) {
            return new Long(StringUtils.substringAfter(value, BaseConstants.XML_DATA_TYPE_LONG));
        } else if(StringUtils.startsWith(value, BaseConstants.XML_DATA_TYPE_FLOAT)) {
            return new Float(StringUtils.substringAfter(value, BaseConstants.XML_DATA_TYPE_FLOAT));
        } else if(StringUtils.startsWith(value, BaseConstants.XML_DATA_TYPE_DOUBLE)) {
            return new Double(StringUtils.substringAfter(value, BaseConstants.XML_DATA_TYPE_DOUBLE));
        } else if(StringUtils.startsWith(value, BaseConstants.XML_DATA_TYPE_DATE)) {
            SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sb.parse(StringUtils.substringAfter(value, BaseConstants.XML_DATA_TYPE_DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
    
    /**
     * 使用此方法前请明确知道它的使用方式.
     * <p>
     * 此方法必须和getPropertity成对使用！
     * 
     * @param key
     * @param value
     */
    public void putPropertity(Object key, Object value){
        if(value instanceof ReferenceType) {
            value =  BaseConstants.XML_DATA_TYPE_FUNCTION + ((ReferenceType)value).getReference();
        } else if(value instanceof Integer) {
            value =  BaseConstants.XML_DATA_TYPE_INTEGER + value.toString();
        } else if(value instanceof Double) {
            value =  BaseConstants.XML_DATA_TYPE_DOUBLE + value.toString();
        } else if(value instanceof Float) {
            value =  BaseConstants.XML_DATA_TYPE_FLOAT + value.toString();
        } else if(value instanceof Long) {
            value =  BaseConstants.XML_DATA_TYPE_LONG + value.toString();
        } else if(value instanceof Boolean) {
            Boolean v = (Boolean) value;
            value =  BaseConstants.XML_DATA_TYPE_BOOLEAN + (v ? "true" : "false");
        }  else if(value instanceof Date) {
            value =  BaseConstants.XML_DATA_TYPE_DATE + DateFormatUtils.format((Date)value, "yyyy-MM-dd");
        } 
        put(key, value);
    }



    public String getString(Object key) {
        return MapUtils.getString(this, key);
    }

    public String getString(Object key, String defaultValue) {
        return MapUtils.getString(this, key, defaultValue);
    }


    public Boolean getBoolean(Object key) {
        return MapUtils.getBoolean(this, key);
    }

    public Boolean getBoolean(Object key, Boolean defaultValue) {
        return MapUtils.getBoolean(this, key, defaultValue);
    }


    public Integer getInteger(Object key) {
        return MapUtils.getInteger(this, key);
    }

    public Integer getInteger(Object key, int defaultValue) {
        return MapUtils.getInteger(this, key, defaultValue);
    }
    
    public Long getLong(Object key) {
        return MapUtils.getLong(this, key);
    }

    public Long getLong(Object key, Long defaultValue) {
        return MapUtils.getLong(this, key, defaultValue);
    }


    public Short getShort(Object key) {
        return MapUtils.getShort(this, key);
    }

    public Short getShort(Object key, Short defaultValue) {
        return MapUtils.getShort(this, key, defaultValue);
    }

    public Double getDouble(Object key) {
        return MapUtils.getDouble(this, key);
    }

    public Double getDouble(Object key, Double defaultValue) {
        return MapUtils.getDouble(this, key, defaultValue);
    }
    
    public Float getFloat(Object key) {
        return MapUtils.getFloat(this, key);
    }

    public Float getFloat(Object key, Float defaultValue) {
        return MapUtils.getFloat(this, key, defaultValue);
    }


    public Byte getByte(Object key) {
        return MapUtils.getByte(this, key);
    }

    public byte getByte(Object key, byte defaultValue) {
        return MapUtils.getByte(this, key, defaultValue);
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }
    
    
    public Map<String,String> getNamespaceMapping(){
        return namespaceMapping;
    }
    
    public void setNamespaceMapping( Map<String,String> mapping ){
        namespaceMapping = mapping;
    }
    
    public String getAbsolutePath(){
        StringBuilder path = new StringBuilder();
        processPath(path);
        return path.toString();
    }
    
    protected void processPath(StringBuilder sb){
        if(this.getParent()!=null){
            this.getParent().processPath(sb);
        }
        sb.append("/").append(this.getPrefix()).append(":").append(this.getName());
        
    }
    
    private int iterateChild(IterationHandle handle, boolean root_first) {
        int result = IterationHandle.IT_CONTINUE;
        if (children == null) {
            return result;
        }
        ListIterator<XMap> it = children.listIterator();
        while (it.hasNext()) {
            XMap child = (XMap) it.next();
            result = child.iterate(handle, root_first);
            if (result == IterationHandle.IT_BREAK) {
                return result;
            } else if (result == IterationHandle.IT_REMOVE) {
                it.remove();
                result = IterationHandle.IT_CONTINUE;
            }
        }
        return result;
    }
    
    public int iterate(IterationHandle handle, boolean root_first) {
        int result;
        if (root_first) {
            result = handle.process(this);
            if (result == IterationHandle.IT_CONTINUE) {
                result = iterateChild(handle, root_first);
            }
            return result;
        } else {
            result = iterateChild(handle, root_first);
            if (result != IterationHandle.IT_BREAK)
                handle.process(this);
            return result;
        }
    }
    
    
    public static void addBook(XMap books, String categoryName, String bookName, String bookPrice){
        List<XMap> categories = books.getChildren();
        if(categories == null){
            categories = new ArrayList<>();
            books.addChild(categories);
        }
        XMap category = null;
        for(XMap cat : categories){
            if(categoryName.equals(cat.getString("type"))){
                category = cat;
                break;
            }
            
        }
        if(category == null){
            category = new XMap("category");
            category.put("type", categoryName);
            books.addChild(category);
        }
        
        if(category!=null){
            XMap book = new XMap("book");
            book.put("name", bookName);
            XMap price1 = new XMap("price");
            price1.setText(bookPrice);
            book.addChild(price1);
            category.addChild(book);
        }
        
    }
    
    public static void main(String[] args) {
        XMap library = new XMap("library");
        XMap booksa = new XMap("books");
        booksa.put("group", "a");
        addBook(booksa, "java", "java0","11");
        addBook(booksa, "java", "java1","12");
        addBook(booksa, "java", "java2","13");
        
        addBook(booksa, "javascript", "javascript0","14");
        addBook(booksa, "javascript", "javascript1","15");
        addBook(booksa, "javascript", "javascript2","16");
        
        
        XMap booksb = new XMap("books");
        booksb.put("group", "b");
        addBook(booksb, "java", "java0","21");
        addBook(booksb, "java", "java1","22");
        addBook(booksb, "java", "java2","23");
        
        addBook(booksb, "javascript", "javascript0","24");
        addBook(booksb, "javascript", "javascript1","25");
        addBook(booksb, "javascript", "javascript22","26");
        
        library.addChild(booksa);
        library.addChild(booksb);
        
        System.out.println(library.toXML());
        
        Map<String,String> mapping = new HashMap<>();
        mapping.put(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE);
        
//        XPathRequest xpath = new XPathRequest("/h:library/h:books[@group='b']/h:category[@type='javascript']/h:book[@name='javascript22']/h:price", mapping);
//        XPathRequest xpath = new XPathRequest("/h:library/h:books[@group='b']/h:category[@type='javascript']/h:book[1]/h:price", mapping);
        XPathRequest xpath = new XPathRequest("/h:library/h:books[@group='b']/h:category[@type='javascript']/h:book[last()-1]/h:price", mapping);
        
        
        XPathFinder cf = new XPathFinder(xpath);
        XMap result = cf.find(library);
        if(result!=null){
            System.out.println(result.toXML());
        }
    }
}

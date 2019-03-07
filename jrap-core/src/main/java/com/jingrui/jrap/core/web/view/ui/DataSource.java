package com.jingrui.jrap.core.web.view.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 数据源
 * 
 * @author hailin.xu@jingrui.com
 *
 */
@UITag
public class DataSource extends Component {

    private static final String PROPERTITY_DATA = "data";
    private static final String PROPERTITY_TRANSPORT = "transport";
    public static final String PROPERTITY_SCHEMA = "schema";
    private static final String PROPERTITY_AUTO_SYNC = "autoSync";
    private static final String PROPERTITY_BATCH = "batch";
    private static final String PROPERTITY_PAGE = "page";
    private static final String PROPERTITY_PAGE_SIZE = "pageSize";
    private static final String PROPERTITY_SERVER_AGGREGATES = "serverAggregates";
    private static final String PROPERTITY_SERVER_FILTERING = "serverFiltering";
    private static final String PROPERTITY_SERVER_GROUPING = "serverGrouping";
    private static final String PROPERTITY_SERVER_PAGING = "serverPaging";
    private static final String PROPERTITY_SERVER_SORTING = "serverSorting";
    private static final String PROPERTITY_TYPE = "type";
    private static final String PROPERTITY_READ = "read";
    private static final String PROPERTITY_CREATE = "create";
    private static final String PROPERTITY_UPDATE = "update";
    private static final String PROPERTITY_DESTROY = "destroy";
    private static final String PROPERTITY_PARAMETER_MAP = "parameterMap";
    private static final String PROPERTITY_CHANGE = "change";
    private static final String PROPERTITY_ERROR = "error";
    private static final String PROPERTITY_PUSH = "push";
    private static final String PROPERTITY_REQUEST_END = "requestEnd";
    private static final String PROPERTITY_REQUEST_START = "requestStart";
    private static final String PROPERTITY_SYNC = "sync";
    private static final String PROPERTITY_SORT = "sort";
    private static final String PROPERTITY_DIR = "dir";
    private static final String PROPERTITY_ITEM = "item";
    private static final String PROPERTITY_COMPARE = "compare";
    private static final String PROPERTITY_DATA_SOURCE = "dataSource";
    private static final String PROPERTITY_FIELD = "field";

    public static DataSource createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_DATA_SOURCE);
        DataSource dataSource = new DataSource();
        dataSource.initPrototype(view);
        return dataSource;
    }

    public String getData() {
        return getPrototype().getString(PROPERTITY_DATA);
    }

    public void setData(String data) {
        setPropertity(PROPERTITY_DATA, data);
    }

    public List<XMap> getDatas() {
        XMap data = getPrototype().getChild(PROPERTITY_DATA);
        if (data != null) {
            return data.getChildren();
        }
        return null;
    }

    // setDatas
    public void setDatas(List<Map<String, Object>> datas) {
        XMap data = getPrototype().getChild(PROPERTITY_DATA);
        if (data == null) {
            data = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_DATA);
        }
        List<XMap> list = new ArrayList<>();
        if(datas!=null){
            for (Map<String, Object> map : datas) {
                XMap x = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_ITEM);
                if(map!=null){
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        x.put(entry.getKey(), entry.getValue());
                    }  
                    list.add(x);
                }
            }    
        }
        data.addChild(list);
        
    }

    public Boolean getAutoSync() {
        return getPrototype().getBoolean(PROPERTITY_AUTO_SYNC);
    }

    public void setAutoSync(Boolean autoSync) {
        setPropertity(PROPERTITY_AUTO_SYNC, autoSync);
    }

    public Boolean getBatch() {
        return getPrototype().getBoolean(PROPERTITY_BATCH);
    }

    public void setBatch(Boolean batch) {
        setPropertity(PROPERTITY_BATCH, batch);
    }

    public Integer getPage() {
        return getPrototype().getInteger(PROPERTITY_PAGE);
    }

    public void setPage(Integer page) {
        setPropertity(PROPERTITY_PAGE, page);
    }

    public Integer getPageSize() {
        return getPrototype().getInteger(PROPERTITY_PAGE_SIZE);
    }

    public void setPageSize(Integer pageSize) {
        setPropertity(PROPERTITY_PAGE_SIZE, pageSize);
    }

    public Boolean getServerAggregates() {
        return getPrototype().getBoolean(PROPERTITY_SERVER_AGGREGATES);
    }

    public void setServerAggregates(Boolean serverAggregates) {
        setPropertity(PROPERTITY_SERVER_AGGREGATES, serverAggregates);
    }

    public Boolean getServerFiltering() {
        return getPrototype().getBoolean(PROPERTITY_SERVER_FILTERING);
    }

    public void setServerFiltering(Boolean serverFiltering) {
        setPropertity(PROPERTITY_SERVER_FILTERING, serverFiltering);
    }

    public Boolean getServerGrouping() {
        return getPrototype().getBoolean(PROPERTITY_SERVER_GROUPING);
    }

    public void setServerGrouping(Boolean serverGrouping) {
        setPropertity(PROPERTITY_SERVER_GROUPING, serverGrouping);
    }

    public Boolean getServerPaging() {
        return getPrototype().getBoolean(PROPERTITY_SERVER_PAGING);
    }

    public void setServerPaging(Boolean serverPaging) {
        setPropertity(PROPERTITY_SERVER_PAGING, serverPaging);
    }

    public Boolean getServerSorting() {
        return getPrototype().getBoolean(PROPERTITY_SERVER_SORTING);
    }

    public void setServerSorting(Boolean serverSorting) {
        setPropertity(PROPERTITY_SERVER_SORTING, serverSorting);
    }

    public String getType() {
        return getPrototype().getString(PROPERTITY_TYPE);
    }

    public void setType(String type) {
        setPropertity(PROPERTITY_TYPE, type);
    }

    public Object getSort() {
        XMap sort = getPrototype().getChild(PROPERTITY_SORT);
        List<XMap> map = new ArrayList<>();
        if (sort != null) {
            List<XMap> list = sort.getChildren();
            if (list != null) {
                for (XMap child : list) {
                    String compare = child.getString(PROPERTITY_COMPARE);
                    if (compare != null) {
                        child.put(PROPERTITY_COMPARE, new ReferenceType(compare));
                    }
                    map.add(child);
                }
                return map;
            }
            return sort;
        }
        return null;
    }

    public void addSort(String dir, String field, ReferenceType compare) {
        XMap sort = getPrototype().getChild(PROPERTITY_SORT);
        if (sort == null) {
            sort = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_SORT);
        }
        sort.put(PROPERTITY_DIR, dir);
        sort.put(PROPERTITY_FIELD, field);
        sort.put(PROPERTITY_COMPARE, compare);
    }

    public void setSort(List<Map<String, Object>> sorts) {
        XMap sort = getPrototype().getChild(PROPERTITY_SORT);
        if (sort == null) {
            sort = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_SORT);
        }
        List<XMap> list = new ArrayList<>();
        for (Map<String, Object> map : sorts) {
            XMap x = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "item");
            if(map!=null){
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    x.put(entry.getKey(), entry.getValue());
                }
                list.add(x); 
            }
        }
        sort.addChild(list);
    }

    public Object getSchema() {
        XMap map = getPrototype().getChild(PROPERTITY_SCHEMA);
        if (map != null) {
            return Schema.parseXMap(map);
        }
        return null;
    }

    public void setSchema(Schema schema) {
        getPrototype().addChild(schema.toXMap());
    }

    public Object getTransport() {
        Transport transport = new Transport();
        XMap map = getPrototype().getChild(PROPERTITY_TRANSPORT);
        if (map != null) {
            transport.setRead(TransportMethod.parse(map.getChild(PROPERTITY_READ)));
            transport.setCreate(TransportMethod.parse(map.getChild(PROPERTITY_CREATE)));
            transport.setUpdate(TransportMethod.parse(map.getChild(PROPERTITY_UPDATE)));
            transport.setDestroy(TransportMethod.parse(map.getChild(PROPERTITY_DESTROY)));
            if (map.getString(PROPERTITY_PARAMETER_MAP) != null) {
                transport.setParameterMap(new ReferenceType(map.getString(PROPERTITY_PARAMETER_MAP)));
            }
            if (map.getString(PROPERTITY_PUSH) != null) {
                transport.setPush(new ReferenceType(map.getString(PROPERTITY_PUSH)));
            }
            return transport;
        }
        return null;
    }

    public void setTransport(Transport transport) {
        XMap map = getPrototype().getChild(PROPERTITY_TRANSPORT);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_TRANSPORT);
        }
        map.addChild(transport.toXMap());
    }

    public void setModel(Model model) {
        XMap schema = getPrototype().getChild(PROPERTITY_SCHEMA);
        if (schema == null) {
            schema = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_SCHEMA);
        }
        schema.addChild(model.toXMap());
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_CHANGE, PROPERTITY_ERROR, PROPERTITY_PUSH, PROPERTITY_REQUEST_END,
                PROPERTITY_REQUEST_START, PROPERTITY_SYNC);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_DATA, getData());
        context.addJsonConfig(PROPERTITY_DATA, getDatas());
        context.addJsonConfig(PROPERTITY_TRANSPORT, getTransport());
        context.addJsonConfig(PROPERTITY_SCHEMA, getSchema());
        context.addJsonConfig(PROPERTITY_AUTO_SYNC, getAutoSync());
        context.addJsonConfig(PROPERTITY_BATCH, getBatch());
        context.addJsonConfig(PROPERTITY_PAGE, getPage());
        context.addJsonConfig(PROPERTITY_PAGE_SIZE, getPageSize());
        context.addJsonConfig(PROPERTITY_SERVER_AGGREGATES, getServerAggregates());
        context.addJsonConfig(PROPERTITY_SERVER_FILTERING, getServerFiltering());
        context.addJsonConfig(PROPERTITY_SERVER_GROUPING, getServerGrouping());
        context.addJsonConfig(PROPERTITY_SERVER_PAGING, getServerPaging());
        context.addJsonConfig(PROPERTITY_SERVER_SORTING, getServerSorting());
        context.addJsonConfig(PROPERTITY_TYPE, getType());
        context.addJsonConfig(PROPERTITY_SORT, getSort());
    }
}
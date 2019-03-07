package com.jingrui.jrap.system.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.cache.impl.LovCache;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.IRequestAware;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.mybatis.util.SqlMapper;
import com.jingrui.jrap.system.dto.DTOClassInfo;
import com.jingrui.jrap.system.dto.Lov;
import com.jingrui.jrap.system.dto.LovItem;
import com.jingrui.jrap.system.mapper.LovItemMapper;
import com.jingrui.jrap.system.mapper.LovMapper;
import com.jingrui.jrap.system.service.ILovService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 *  通用lov服务接口 - 实现类.
 *
 * @author njq.niu@jingrui.com
 * @date 2016/1/31
 */
@Service
public class LovServiceImpl extends BaseServiceImpl<Lov> implements ILovService {

    private final Logger logger = LoggerFactory.getLogger(LovServiceImpl.class);

    @Autowired
    @Qualifier("sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LovMapper lovMapper;

    @Autowired
    private LovItemMapper lovItemMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LovCache lovCache;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Lov> selectLovs(IRequest request, Lov lov, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return lovMapper.selectLovs(lov);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Lov loadLov(Long lovId) {
        return lovMapper.selectByPrimaryKey(lovId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Lov> batchUpdate(IRequest request,@StdWho List<Lov> lovs) {
        for (Lov lov : lovs) {
            if (lov.getLovId() == null) {
                self().createLov(lov);
            } else if (lov.getLovId() != null) {
                self().updateLov(lov);
            }
        }
        return lovs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteLov(List<Lov> items) {
        for (Lov lov : items) {
            int updateCount = lovMapper.deleteByPrimaryKey(lov);
            checkOvn(updateCount, lov);
            lovItemMapper.deleteByLovId(lov.getLovId());
            lovCache.remove(lov.getCode());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteItems(List<LovItem> items) {
        for (LovItem item : items) {
            self().deleteLovItem(item);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteLovItem(LovItem item) {
        int updateCount = lovItemMapper.deleteByPrimaryKey(item);
        checkOvn(updateCount, item);
        if (1 == updateCount) {
            lovCache.reload(item.getLovId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Lov createLov(Lov lov) {
        lovMapper.insertSelective(lov);
        if (lov.getLovItems() != null) {
            for (LovItem lovItem : lov.getLovItems()) {
                lovItem.setLovId(lov.getLovId());
                lovItemMapper.insertSelective(lovItem);
            }
        }
        lovCache.reload(lov.getLovId());
        return lov;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Lov updateLov(Lov lov) {
        int updateCount = lovMapper.updateByPrimaryKeySelective(lov);
        checkOvn(updateCount, lov);
        if (lov.getLovItems() != null) {
            for (LovItem lovItem : lov.getLovItems()) {
                if (lovItem.getLovItemId() == null) {
                    lovItem.setLovId(lov.getLovId());
                    lovItemMapper.insertSelective(lovItem);
                } else {
                    lovItemMapper.updateByPrimaryKeySelective(lovItem);
                }
            }
        }
        lovCache.reload(lov.getLovId());
        return lov;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<LovItem> selectLovItems(IRequest request, LovItem lovItem) {
        return lovItemMapper.selectByLovId(lovItem.getLovId());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String getLov(String contextPath, Locale locale, String lovCode) {
        LovEditor editor = getLovEditor(contextPath, locale, lovCode);
        try {
            return objectMapper.writeValueAsString(editor);
        } catch (JsonProcessingException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return "''";
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<?> selectDatas(IRequest request, String code, Object obj, int page, int pagesize) {
        Lov lov = lovCache.getValue(code);
        String sqlId = lov.getSqlId();
        String customSql = lov.getCustomSql();
        String customUrl = lov.getCustomUrl();
        if (StringUtils.isNotEmpty(sqlId)) {
            String beanName = StringUtils.uncapitalize(StringUtils.substringBefore(lov.getSqlId(), "."));
            Object mapperObjectDelegate = beanFactory.getBean(beanName);
            if (mapperObjectDelegate == null) {
                return Collections.emptyList();
            }
            Class<?>[] interfaceClass = mapperObjectDelegate.getClass().getInterfaces();
            for (Class c : interfaceClass) {
                if (c.getSimpleName().equalsIgnoreCase(beanName)) {
                    sqlId = c.getPackage().getName() + "." + StringUtils.capitalize(lov.getSqlId());
                    break;
                }
            }
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                PageHelper.startPage(page, pagesize);
                obj = convertMapParamToDtoParam(sqlSession, sqlId, obj);
                if (obj instanceof IRequestAware) {
                    ((IRequestAware) obj).setRequest(request);
                } else if (obj instanceof Map) {
                    ((Map) obj).put("request", request);
                }
                return sqlSession.selectList(sqlId, obj);
            } catch (Throwable e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else if (StringUtils.isNotEmpty(customSql)) {
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                PageHelper.startPage(page, pagesize);
                SqlMapper sqlMapper = new SqlMapper(sqlSession);
                if (obj instanceof Map) {
                    ((Map) obj).put("request", request);
                }
                List<HashMap> preResults = sqlMapper.selectList("<script>\n\t" + customSql + "</script>", obj,
                        HashMap.class);
                Page results = new Page();
                results.setTotal(((Page) preResults).getTotal());
                for (HashMap m0 : preResults) {
                    HashMap map = new HashMap(16);
                    m0.forEach((k, v) -> map.put(DTOClassInfo.underLineToCamel((String) k), v));
                    results.add(map);
                }
                return results;
            } catch (Throwable e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return Collections.emptyList();
    }

    private LovEditor getLovEditor(String contextPath, Locale locale, String lovCode) {
        Lov lov = lovCache.getValue(lovCode);
        if (lov == null) {
            lov = lovMapper.selectByCode(lovCode);
            if (lov != null) {
                List<LovItem> items = lovItemMapper.selectByLovId(lov.getLovId());
                lov.setLovItems(items);
                lovCache.setValue(lov.getCode(), lov);
            }
        }

        return lov != null ? createLovEditor(contextPath, locale, lov, lov.getLovItems()) : null;
    }

    /**
     * 将 map 类型的参数 转换为 dto 类型.
     *
     * @param sqlSession
     * @param sqlId
     * @param map
     * @return
     */
    private Object convertMapParamToDtoParam(SqlSession sqlSession, String sqlId, Object map) {
        if (!(map instanceof Map)) {
            logger.warn("lov query parameter is not a map:{}", map);
            return map;
        }
        MappedStatement statement = sqlSession.getConfiguration().getMappedStatement(sqlId);
        if (statement == null) {
            logger.warn("no statement found for sqlId:{}", sqlId);
            return map;
        }
        List<ResultMap> resultMaps = statement.getResultMaps();
        if (resultMaps == null || resultMaps.isEmpty()) {
            logger.warn("statement has no specified ResultMap, sqlId:{}", sqlId);
            return map;
        }
        ResultMap resultMap = resultMaps.get(0);
        try {
            Class dtoClass = resultMap.getType();
            Object dto = dtoClass.newInstance();
            if (dto instanceof Map) {
                return map;
            }
            ((Map) map).forEach((k, v) -> {
                try {
                    PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(dto, (String) k);
                    if (desc != null) {
                        BeanUtils.setProperty(dto, (String) k, v);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            logger.debug("convert lov query parameter to {}", dto);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private LovEditor createLovEditor(String contextPath, Locale locale, Lov lov, List<LovItem> items) {
        LovEditor editor = new LovEditor(lov, locale);
        editor.setGrid(new LovGrid(contextPath, locale, lov, items));
        editor.setCondition(new LovCondition(contextPath, locale, lov, items));
        return editor;
    }

    /**
     * lov配置对象.
     *
     * @author njq.niu@jingrui.com
     * @DATE 2016/1/31
     */
    private class LovEditor {

        LovEditor(Lov lov, Locale locale) {
            if (lov != null) {
                String title = messageSource.getMessage(lov.getTitle(), null, lov.getTitle(), locale);
                setTitle(title);
                setTextField(lov.getTextField());
                setValueField(lov.getValueField());
                setPlaceholder(messageSource.getMessage(lov.getPlaceholder(), null, lov.getTitle(), locale));
                if (BaseConstants.YES.equals(lov.getEditableFlag())) {
                    setReadonly(false);
                }
                setPopup(BaseConstants.YES.equals(lov.getCanPopup()));
            }
            for (LovItem item : lov.getLovItems()) {
                if (BaseConstants.YES.equals(item.getIsAutocomplete())) {
                    setAutocomplete(true);
                    setAutocompleteField(item.getGridFieldName());
                    break;
                }
            }
        }

        private String type = "popup";

        private String valueField;

        private String textField;

        @JsonInclude(Include.NON_NULL)
        private String title;

        @JsonInclude(Include.NON_NULL)
        private String placeholder;

        private LovGrid grid;

        @JsonInclude(Include.NON_NULL)
        private boolean autocomplete;

        @JsonInclude(Include.NON_NULL)
        private Boolean readonly = null;

        @JsonInclude(Include.NON_NULL)
        private boolean popup;

        @JsonInclude(Include.NON_NULL)
        private String autocompleteField;

        private LovCondition condition;

        public boolean getPopup() {
            return popup;
        }

        public void setPopup(boolean popup) {
            this.popup = popup;
        }

        public Boolean getReadonly() {
            return readonly;
        }

        public void setReadonly(Boolean readonly) {
            this.readonly = readonly;
        }

        public String getAutocompleteField() {
            return autocompleteField;
        }

        public void setAutocompleteField(String autocompleteField) {
            this.autocompleteField = autocompleteField;
        }

        public boolean getAutocomplete() {
            return autocomplete;
        }

        public void setAutocomplete(boolean autocomplete) {
            this.autocomplete = autocomplete;
        }

        public LovCondition getCondition() {
            return condition;
        }

        public void setCondition(LovCondition condition) {
            this.condition = condition;
        }

        public LovGrid getGrid() {
            return grid;
        }

        public void setGrid(LovGrid grid) {
            this.grid = grid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValueField() {
            return valueField;
        }

        public void setValueField(String valueField) {
            this.valueField = valueField;
        }

        public String getTextField() {
            return textField;
        }

        public void setTextField(String textField) {
            this.textField = textField;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }
    }

    /**
     * lov Grid配置对象.
     *
     * @author njq.niu@jingrui.com
     * @date 2016/2/1
     */
    class LovGrid {

        LovGrid(String contextPath, Locale locale, Lov lov, List<LovItem> items) {
            setUrl(contextPath + "/common/lov/" + lov.getCode());
            setWidth(lov.getWidth());
            setHeight(lov.getHeight());
            setDelayLoad(BaseConstants.YES.equalsIgnoreCase(lov.getDelayLoad()));
            setNeedQueryParam(BaseConstants.YES.equalsIgnoreCase(lov.getNeedQueryParam()));
            if (items != null) {
                for (LovItem item : items) {
                    if (BaseConstants.YES.equalsIgnoreCase(item.getGridField())) {
                        addColumn(new LovGridColumn(contextPath, locale, item));
                    }
                }
                columns.sort(Comparator.comparing(LovGridColumn::getSequence));
            }
        }

        @JsonInclude(Include.NON_NULL)
        private Integer width;

        @JsonInclude(Include.NON_NULL)
        private Integer height;

        private boolean delayLoad = false;

        private boolean needQueryParam = false;

        private String url;

        private boolean isSingleCheck = true;

        private List<LovGridColumn> columns = new ArrayList<>();

        public Boolean getNeedQueryParam() {
            return needQueryParam;
        }

        public void setNeedQueryParam(boolean needQueryParam) {
            this.needQueryParam = needQueryParam;
        }

        public Boolean getDelayLoad() {
            return delayLoad;
        }

        public void setDelayLoad(boolean delayLoad) {
            this.delayLoad = delayLoad;
        }

        public boolean getIsSingleCheck() {
            return isSingleCheck;
        }

        public void setIsSingleCheck(boolean isSingleCheck) {
            this.isSingleCheck = isSingleCheck;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void addColumn(LovGridColumn column) {
            columns.add(column);
        }

        public List<LovGridColumn> getColumns() {
            return columns;
        }

        public void setColumns(List<LovGridColumn> columns) {
            this.columns = columns;
        }

    }

    /**
     * @author njq.niu@jingrui.com
     * @date 2016/2/1
     */
    class LovGridColumn {

        @JsonIgnore
        private Integer sequence = 1;

        private String display;

        private String name;

        @JsonInclude(Include.NON_NULL)
        private String align;

        private boolean isSort = false;

        private boolean autocompleteField = false;

        @JsonInclude(Include.NON_NULL)
        private Integer width;

        LovGridColumn(String contextPath, Locale locale, LovItem item) {
            String display = messageSource.getMessage(item.getDisplay(), null, item.getDisplay(), locale);
            setDisplay(display);
            setName(item.getGridFieldName());
            setSequence(item.getGridFieldSequence());
            setAlign(item.getGridFieldAlign());
            setWidth(item.getGridFieldWidth());
            setAutocompleteField(BaseConstants.YES.equals(item.getAutocompleteField()));

        }

        public String getAlign() {
            return align;
        }

        public void setAlign(String align) {
            this.align = align;
        }

        public Integer getSequence() {
            return sequence;
        }

        public void setSequence(Integer sequence) {
            this.sequence = sequence;
        }

        public boolean getAutocompleteField() {
            return autocompleteField;
        }

        public void setAutocompleteField(boolean autocompleteField) {
            this.autocompleteField = autocompleteField;
        }

        public boolean getIsSort() {
            return isSort;
        }

        public void setIsSort(boolean isSort) {
            this.isSort = isSort;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }
    }

    /**
     * @author njq.niu@jingrui.com
     * @date 2016/2/1
     */
    class LovCondition {

        LovCondition(String contextPath, Locale locale, Lov lov, List<LovItem> items) {
            for (LovItem item : items) {
                if (BaseConstants.YES.equalsIgnoreCase(item.getConditionField())) {
                    addField(new LovConditionField(contextPath, locale, item));
                }
            }
            fields.sort(Comparator.comparing(LovConditionField::getSequence));
        }

        private List<LovConditionField> fields = new ArrayList<>();

        public List<LovConditionField> getFields() {
            return fields;
        }

        public void setFields(List<LovConditionField> fields) {
            this.fields = fields;
        }

        public void addField(LovConditionField f) {
            fields.add(f);
        }

    }

    /**
     * @author njq.niu@jingrui.com
     * @date 2016/2/1
     */
    class LovConditionField extends LovGridColumn {

        LovConditionField(String contextPath, Locale locale, LovItem item) {
            super(contextPath, locale, item);
            setName(item.getConditionFieldName() == null ? item.getGridFieldName() : item.getConditionFieldName());
            if (item.getConditionFieldTextfield() != null) {
                setTextField(item.getConditionFieldTextfield());
            }

            setType(item.getConditionFieldType());
            setWidth(item.getConditionFieldWidth());
            setNewline(BaseConstants.YES.equalsIgnoreCase(item.getConditionFieldNewline()));
            setSequence(item.getConditionFieldSequence());
            // select
            if (item.getConditionFieldSelectCode() != null || item.getConditionFieldSelectUrl() != null) {
                setOptions(new LovConditionFieldSelectOption(contextPath, item));
            } else if (item.getConditionFieldLovCode() != null) {
                LovEditor editor = getLovEditor(contextPath, locale, item.getConditionFieldLovCode());
                setOptions(editor);
            }

        }

        private String type;
        private boolean newline;

        @JsonInclude(Include.NON_NULL)
        private String textField;

        @JsonInclude(Include.NON_NULL)
        private Object options;

        public String getTextField() {
            return textField;
        }

        public void setTextField(String textField) {
            this.textField = textField;
        }

        public Object getOptions() {
            return options;
        }

        public void setOptions(Object options) {
            this.options = options;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isNewline() {
            return newline;
        }

        public void setNewline(boolean newline) {
            this.newline = newline;
        }
    }

    /**
     * @author njq.niu@jingrui.com
     * @date 2016/3/14
     */
    class LovConditionFieldSelectOption {

        LovConditionFieldSelectOption(String contextPath, LovItem item) {
            if (item.getConditionFieldSelectCode() != null) {
                setUrl(contextPath + "/common/code/" + item.getConditionFieldSelectCode() + "/");
                setValueField("value");
                setTextField("meaning");
            } else if (item.getConditionFieldSelectUrl() != null) {
                setUrl(contextPath + item.getConditionFieldSelectUrl());
                setValueField(item.getConditionFieldSelectVf());
                setTextField(item.getConditionFieldSelectTf());
            }
        }

        private String valueField;

        private String textField;

        private String url;

        public String getValueField() {
            return valueField;
        }

        public void setValueField(String valueField) {
            this.valueField = valueField;
        }

        public String getTextField() {
            return textField;
        }

        public void setTextField(String textField) {
            this.textField = textField;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

}
package com.jingrui.jrap.flexfield.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.flexfield.dto.FlexRule;
import com.jingrui.jrap.flexfield.dto.FlexRuleDetail;
import com.jingrui.jrap.flexfield.dto.FlexRuleField;
import com.jingrui.jrap.flexfield.dto.WarpFlexRuleField;
import com.jingrui.jrap.flexfield.mapper.FlexRuleDetailMapper;
import com.jingrui.jrap.flexfield.mapper.FlexRuleFieldMapper;
import com.jingrui.jrap.flexfield.mapper.FlexRuleMapper;
import com.jingrui.jrap.flexfield.service.IFlexRuleService;
import com.jingrui.jrap.generator.service.impl.FileUtil;
import com.jingrui.jrap.system.dto.BaseDTO;
import com.jingrui.jrap.system.dto.Lov;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.ILovService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import jodd.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlexRuleServiceImpl extends BaseServiceImpl<FlexRule> implements IFlexRuleService {

    @Autowired
    FlexRuleMapper ruleMapper;

    @Autowired
    FlexRuleFieldMapper fieldMapper;

    @Autowired
    FlexRuleDetailMapper flexRuleDetailMapper;

    @Autowired
    FlexRuleFieldMapper FlexRuleFieldMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ILovService lovService;

    @Override
    public ResponseData matchingRule(String ruleSetCode, Set<Map.Entry<String, String>> model, IRequest iRequest) {
        List<FlexRule> flexRules = ruleMapper.matchingRule(ruleSetCode);
        Iterator iterator=flexRules.iterator();
        FlexRule baseFlex=null;
        while(iterator.hasNext()){
            FlexRule fl= (FlexRule) iterator.next();
            if(fl.getRuleCode().equals("_GLOBAL_FLEX_RULE")){
                baseFlex=fl;
                iterator.remove();
                break;
            }
        }


        if (!flexRules.isEmpty()) {
            FlexRule flexRule = flexRules.stream()
                    .filter(flexRule1 -> matching(flexRule1, model))
                    .max(Comparator.comparing(flexRule2 -> flexRule2.getFlexRuleDetailList().size())).orElseGet(() -> new FlexRule());

            if (null != flexRule.getRuleId()) {
                FlexRuleField flexRuleField = new FlexRuleField();
                flexRuleField.setRuleId(flexRule.getRuleId());
                List<FlexRuleField> flexRuleFields=fieldMapper.queryFlexField(flexRuleField);
                if(null !=baseFlex) {
                    flexRuleField.setRuleId(baseFlex.getRuleId());
                    flexRuleFields.addAll(fieldMapper.queryFlexField(flexRuleField));
                }
                return new ResponseData(classify(flexRuleFields));
            }
        }else if(null !=baseFlex) {
            FlexRuleField flexRuleField = new FlexRuleField();
            flexRuleField.setRuleId(baseFlex.getRuleId());
            return new ResponseData(classify(fieldMapper.queryFlexField(flexRuleField)));
        }
        return new ResponseData();
    }

    public void matchingLovField(String ruleSetCode, Set<Map.Entry<String, String>> model, Object o,IRequest iRequest) throws IllegalAccessException, IOException, InvocationTargetException, NoSuchMethodException {
        List<FlexRule> flexRules = ruleMapper.matchingRule(ruleSetCode);
        if (!flexRules.isEmpty()) {
            FlexRule flexRule = flexRules.stream()
                    .filter(flexRule1 -> matching(flexRule1, model))
                    .max(Comparator.comparing(flexRule2 -> flexRule2.getFlexRuleDetailList().size())).orElseGet(() -> new FlexRule());
            if (null != flexRule.getRuleId()) {
                FlexRuleField flexRuleField = new FlexRuleField();
                flexRuleField.setRuleId(flexRule.getRuleId());
                List<FlexRuleField> fields = fieldMapper.queryFlexField(flexRuleField);
                for (int i = 0; i < fields.size(); i++) {
                    getLovField(fields.get(i), o,iRequest);
                }
            }

        }
    }

    public void getLovField(FlexRuleField flexRuleField, Object o,IRequest iRequest) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        ObjectNode description = objectMapper.readValue(flexRuleField.getFieldType(), ObjectNode.class);

        if ("LOV".equals(description.get("type").asText())) {
            String lovCode = description.get("conditionFieldLovCode").asText();
            String text = description.get("conditionFieldSelectTf").asText();
            text = text.substring(text.indexOf(".")+1);
            String objectValue=BeanUtils.getProperty(o, FileUtil.columnToCamel(flexRuleField.getColumnName()));
            if(null !=objectValue) {
                Lov lov=new Lov();
                lov.setCode(lovCode);
                lov=lovService.select(iRequest,lov,1,5).get(0);
                Map map = new HashMap();
                map.put(lov.getValueField(), objectValue);
                List<?> list=lovService.selectDatas(iRequest, lovCode, map, 1, 10);
                BaseDTO dto=(BaseDTO)o;
                if(list.size()!=0) {
                    Object o1 =list.get(0);
                    String val = BeanUtils.getProperty(o1, lov.getTextField());
                    dto.setAttribute(text,val);
                }else{
                    dto.setAttribute(text,"");
                }

            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRule(List<FlexRule> flexRules) {
        for (FlexRule flexRule : flexRules) {
            FlexRuleDetail ruleDetail = new FlexRuleDetail();
            FlexRuleField ruleField = new FlexRuleField();
            ruleField.setRuleId(flexRule.getRuleId());
            ruleDetail.setRuleId(flexRule.getRuleId());
            flexRuleDetailMapper.delete(ruleDetail);
            FlexRuleFieldMapper.delete(ruleField);
            int updateCount = ruleMapper.delete(flexRule);
            checkOvn(updateCount, flexRule);
        }
    }

    private List<WarpFlexRuleField> classify(List<FlexRuleField> flexRuleFields) {
        List<WarpFlexRuleField> warpFlexRuleFields = new ArrayList<>();
        flexRuleFields.stream()
                .collect(Collectors.groupingBy(FlexRuleField::getFieldColumnNumber))
                .forEach((k, v) -> {
                    WarpFlexRuleField warpFlexRuleField = new WarpFlexRuleField();
                    // warpFlexRuleField.setLocal(local);
                    warpFlexRuleFields.add(warpFlexRuleField.warpField(v));
                });
        return warpFlexRuleFields;
    }

    public static void setPrompt(WarpFlexRuleField warpFlexRuleField, Locale locale, MessageSource messageSource) {
        warpFlexRuleField.getFields().forEach(v -> {
            doSetPrompt(v, locale, messageSource);
        });
    }

    private static void doSetPrompt(FlexRuleField flexRuleField, Locale locale, MessageSource messageSource) {
        JSONObject josn = JSONObject.fromObject(flexRuleField.getFieldType());
        String name = messageSource.getMessage(josn.get("labelName").toString(), null, locale);
        if (!StringUtil.isEmpty(name)) {
            josn.put("labelName", name);
            flexRuleField.setFieldType(josn.toString());
        }
    }

    private boolean matching(FlexRule flexRule, Set<Map.Entry<String, String>> model) {
        boolean result = false;
        List<FlexRuleDetail> flexRuleDetails = flexRule.getFlexRuleDetailList();
        int sameNumber = 0;
        for (FlexRuleDetail flexRuleDetail1 : flexRuleDetails) {
            Iterator iter = model.iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if (null == entry.getValue()) {
                    continue;
                }
                if (flexRuleDetail1.getFieldValue().equalsIgnoreCase(entry.getValue().toString()) && flexRuleDetail1.getFieldName().equalsIgnoreCase(entry.getKey().toString())) {
                    sameNumber++;
                    break;
                }
            }
        }
        if (sameNumber == flexRuleDetails.size()) {
            result = true;
        }
        return result;
    }
}
package com.jingrui.jrap.core.web.view.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * Grid-PDF
 * 
 * @author hailin.xu@jingrui.com
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GridPDF extends PDF {

    private Boolean allPages;
    private String template;
    private Boolean repeatHeaders;
    private Double scale;

    private static final String PROPERTITY_PDF = "pdf";
    private static final String PROPERTITY_ALL_PAGES = "allPages";
    private static final String PROPERTITY_TEMPLATE = "template";
    private static final String PROPERTITY_REPEAT_HEADERS = "repeatHeaders";
    private static final String PROPERTITY_SCALE = "scale";

    public static GridPDF parseGridPDF(XMap view) {
        GridPDF gridPDF = new GridPDF();
        XMap map = view.getChild(PROPERTITY_PDF);
        if (map != null) {
            gridPDF.setAuthor(map.getString(PROPERTITY_AUTHOR));
            gridPDF.setAvoidLinks(map);
            gridPDF.setCreator(map.getString(PROPERTITY_CREATOR));
            gridPDF.setDate(map.getString(PROPERTITY_DATE));
            gridPDF.setFileName(map.getString(PROPERTITY_FILE_NAME));
            gridPDF.setForceProxy(map.getBoolean(PROPERTITY_FORCE_PROXY));
            gridPDF.setKeywords(map.getString(PROPERTITY_KEYWORDS));
            gridPDF.setLandscape(map.getBoolean(PROPERTITY_LANDSCAPE));
            gridPDF.setMargin(map);
            gridPDF.setPaperSize(map.getString(PROPERTITY_PAPER_SIZE));
            gridPDF.setProxyURL(map.getString(PROPERTITY_PROXY_URL));
            gridPDF.setProxyTarget(map.getString(PROPERTITY_PROXY_TARGET));
            gridPDF.setSubject(map.getString(PROPERTITY_SUBJECT));
            gridPDF.setTitle(map.getString(PROPERTITY_TITLE));
            gridPDF.setAllPages(map.getBoolean(PROPERTITY_ALL_PAGES));
            gridPDF.setTemplate(map.getString(PROPERTITY_TEMPLATE));
            gridPDF.setRepeatHeaders(map.getBoolean(PROPERTITY_REPEAT_HEADERS));
            gridPDF.setScale(map.getDouble(PROPERTITY_SCALE));
            return gridPDF;
        }
        return null;
    }

    public Boolean getAllPages() {
        return allPages;
    }

    public void setAllPages(Boolean allPages) {
        this.allPages = allPages;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Boolean getRepeatHeaders() {
        return repeatHeaders;
    }

    public void setRepeatHeaders(Boolean repeatHeaders) {
        this.repeatHeaders = repeatHeaders;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }
}

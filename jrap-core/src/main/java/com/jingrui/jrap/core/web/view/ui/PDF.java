package com.jingrui.jrap.core.web.view.ui;

import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 表格 treeList - pdf
 * 
 * @author hailin.xu@jingrui.com
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PDF {

    private String author;
    private Object avoidLinks;
    private String creator;
    private Date date;
    private String fileName;
    private Boolean forceProxy;
    private String keywords;
    private Boolean landscape;
    private Object margin;
    private Object paperSize;
    private String proxyURL;
    private String proxyTarget;
    private String subject;
    private String title;

    public static final String PROPERTITY_PDF = "pdf";
    public static final String PROPERTITY_AUTHOR = "author";
    public static final String PROPERTITY_AVOID_LINKS = "avoidLinks";
    public static final String PROPERTITY_CREATOR = "creator";
    public static final String PROPERTITY_DATE = "date";
    public static final String PROPERTITY_FILE_NAME = "fileName";
    public static final String PROPERTITY_FORCE_PROXY = "forceProxy";
    public static final String PROPERTITY_KEYWORDS = "keywords";
    public static final String PROPERTITY_LANDSCAPE = "landscape";
    public static final String PROPERTITY_MARGIN = "margin";
    public static final String PROPERTITY_PAPER_SIZE = "paperSize";
    public static final String PROPERTITY_PROXY_URL = "command";
    public static final String PROPERTITY_PROXY_TARGET = "editor";
    public static final String PROPERTITY_SUBJECT = "command";
    public static final String PROPERTITY_TITLE = "editor";

    public static PDF parsePDF(XMap view) {
        PDF pdf = new PDF();
        XMap map = view.getChild(PROPERTITY_PDF);
        if (map != null) {
            pdf.setAuthor(map.getString(PROPERTITY_AUTHOR));
            pdf.setAvoidLinks(map);
            pdf.setCreator(map.getString(PROPERTITY_CREATOR));
            pdf.setDate(map.getString(PROPERTITY_DATE));
            pdf.setFileName(map.getString(PROPERTITY_FILE_NAME));
            pdf.setForceProxy(map.getBoolean(PROPERTITY_FORCE_PROXY));
            pdf.setKeywords(map.getString(PROPERTITY_KEYWORDS));
            pdf.setLandscape(map.getBoolean(PROPERTITY_LANDSCAPE));
            pdf.setMargin(map);
            pdf.setPaperSize(map.getString(PROPERTITY_PAPER_SIZE));
            pdf.setProxyURL(map.getString(PROPERTITY_PROXY_URL));
            pdf.setProxyTarget(map.getString(PROPERTITY_PROXY_TARGET));
            pdf.setSubject(map.getString(PROPERTITY_SUBJECT));
            pdf.setTitle(map.getString(PROPERTITY_TITLE));
            return pdf;
        }
        return null;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Object getAvoidLinks() {
        return avoidLinks;
    }

    public void setAvoidLinks(XMap map) {
        String avoidLinks = map.getString(PROPERTITY_AVOID_LINKS);
        if (avoidLinks != null) {
            this.avoidLinks = avoidLinks;
            if (avoidLinks.equals("true") || avoidLinks.equals("false")) {
                this.avoidLinks = map.getBoolean(PROPERTITY_AVOID_LINKS);
            }
        }

    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        if (date != null) {
            String[] pattern = new String[] { "yyyy-MM-dd" };
            try {
                this.date = DateUtils.parseDate(date, pattern);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getForceProxy() {
        return forceProxy;
    }

    public void setForceProxy(Boolean forceProxy) {
        this.forceProxy = forceProxy;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getLandscape() {
        return landscape;
    }

    public void setLandscape(Boolean landscape) {
        this.landscape = landscape;
    }

    public Object getMargin() {
        return margin;
    }

    public void setMargin(XMap map) {
        XMap margin = map.getChild(PROPERTITY_MARGIN);
        if (margin != null) {
            String bottom = margin.getString("bottom");
            String left = margin.getString("left");
            String right = margin.getString("right");
            String top = margin.getString("top");
            if (bottom != null) {
                margin.put("bottom", getNumberString(bottom));
            }
            if (left != null) {
                margin.put("left", getNumberString(left));
            }
            if (right != null) {
                margin.put("right", getNumberString(right));
            }
            if (top != null) {
                margin.put("top", getNumberString(top));
            }
            this.margin = margin;
        }

    }

    public Object getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(String paperSize) {
        if (paperSize != null) {
            String[] datas = paperSize.split(",");
            for (int i = 0; i < datas.length; i++) {
                datas[i] = datas[i].trim();
            }
            this.paperSize = paperSize;
        }
    }

    public String getProxyURL() {
        return proxyURL;
    }

    public void setProxyURL(String proxyURL) {
        this.proxyURL = proxyURL;
    }

    public String getProxyTarget() {
        return proxyTarget;
    }

    public void setProxyTarget(String proxyTarget) {
        this.proxyTarget = proxyTarget;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected String getNumberString(String value) {
        try {
            Integer.parseInt(value);
            return value + "px";
        } catch (Exception e) {
            return value;
        }
    }

}

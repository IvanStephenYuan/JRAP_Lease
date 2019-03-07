package com.jingrui.jrap.core.web.view;

import java.io.File;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class ScreenTemplate {

    private File file;

    private String xml;

    private String extend = null;

    private String name;

    private String filePath;

    public ScreenTemplate(File file) {
        setFile(file);
        String path = file.getAbsolutePath();
        setName(path);
        setFilePath(path);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setXML(String xml) {
        this.xml = xml;
    }

    public String getXML() {
        return this.xml;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}

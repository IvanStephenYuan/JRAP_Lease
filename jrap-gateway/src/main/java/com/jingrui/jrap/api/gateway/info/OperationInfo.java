package com.jingrui.jrap.api.gateway.info;

/**
 * 数据传输类.
 *
 * @author lijian.yin@jingrui.com
 **/
public class OperationInfo {

	private String name;

	private String soapActionURI;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSoapActionURI() {
		return soapActionURI;
	}

	public void setSoapActionURI(String soapActionURI) {
		this.soapActionURI = soapActionURI;
	}


}

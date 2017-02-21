package org.ivt.agregator.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseResponse {

    public final static String SUCCESS_STATUS = "SUCCESS";
    public final static String ERROR_STATUS = "ERROR";

    @XmlElement
    private String status = SUCCESS_STATUS;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

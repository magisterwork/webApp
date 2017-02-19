package org.ivt.agregator.integration.culture.entity;

import java.util.List;

public class CultureResponse {

    private ResponseInfo info;
    private List<CultureMaterial> data;

    public ResponseInfo getInfo() {
        return info;
    }

    public void setInfo(ResponseInfo info) {
        this.info = info;
    }

    public List<CultureMaterial> getData() {
        return data;
    }

    public void setData(List<CultureMaterial> data) {
        this.data = data;
    }
}

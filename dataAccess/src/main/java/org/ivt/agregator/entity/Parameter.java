package org.ivt.agregator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PARAMETERS")
public class Parameter {

    public final static String VK_CODE = "vk_code";
    public final static String RATE_STEP = "rate_step";
    public static final String VK_TOKEN = "vk_token";
    public static final String GKEY = "gkey";

    @Id
    @Column(name="KEY_NAME")
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

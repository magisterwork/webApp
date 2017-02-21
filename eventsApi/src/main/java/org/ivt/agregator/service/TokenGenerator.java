package org.ivt.agregator.service;

import java.util.UUID;

public class TokenGenerator {

    public String getToken() {
        return UUID.randomUUID().toString();
    }
}

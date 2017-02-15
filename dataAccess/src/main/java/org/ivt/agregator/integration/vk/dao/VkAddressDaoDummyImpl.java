package org.ivt.agregator.integration.vk.dao;

public class VkAddressDaoDummyImpl implements VkAddressDao {

    public static final String UNKNOWN = "unknown";

    @Override
    public String getCity(String id) {
        if ("41".equals(id)) {
            return "Вологда";
        }
        return UNKNOWN;
    }

    @Override
    public String getCountry(String id) {
        if ("1".equals(id)) {
            return "Россия";
        }
        return UNKNOWN;
    }
}

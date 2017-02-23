package org.ivt.agregator.util;

import org.ivt.agregator.dao.ParameterDao;
import org.ivt.agregator.entity.Parameter;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ParameterLoader {

    public static final String PATH_TO_PROPERTIES = "src/main/resources/parameters.properties";
    protected static final Logger LOGGER = Logger.getLogger(ParameterLoader.class.getName());
    private Properties properties = new Properties();
    private ParameterDao parameterDao;

    public ParameterLoader(ParameterDao parameterDao) {
        this.parameterDao = parameterDao;
        loadProperties();
    }

    private void loadProperties() {
        try (FileInputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(inputStream);
            for (Object key : properties.keySet()) {
                Object value = properties.get(key);
                saveParameter(key, value);
            }
        } catch (Exception e) {
            LOGGER.warning("Не удалось загрузить параметры в БД");
        }
    }

    private void saveParameter(Object key, Object value) {
        Parameter parameter = new Parameter();
        parameter.setKey(String.valueOf(key));
        parameter.setValue(String.valueOf(value));
        parameterDao.save(parameter);
    }
}

package org.ivt.agregator.dao;

import org.apache.commons.lang3.Validate;
import org.ivt.agregator.entity.Parameter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ParameterDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Parameter parameter) {
        Validate.notNull(parameter);
        Validate.notNull(parameter.getKey());
        em.merge(parameter);
    }

    public Parameter get(String key) {
        Validate.notEmpty(key);
        return em.find(Parameter.class, key);
    }
}

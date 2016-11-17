package org.ivt.agregator.dao;

import org.ivt.agregator.entity.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.ivt.agregator.entity.Parameter.*;
import static org.testng.Assert.*;

@ContextConfiguration(locations = "classpath:parameter-dao-test-config.xml")
public class ParameterDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ParameterDao parameterDao;

    @Test
    @Transactional
    public void shouldSaveParameter() {
        Parameter parameter = new Parameter();
        parameter.setKey(VK_CODE);
        parameter.setValue("12345");
        parameterDao.save(parameter);

        assertEquals(em.find(Parameter.class, VK_CODE).getValue(), "12345");
    }
}
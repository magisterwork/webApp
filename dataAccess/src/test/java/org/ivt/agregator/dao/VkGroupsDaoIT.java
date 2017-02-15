package org.ivt.agregator.dao;

import org.ivt.agregator.integration.vk.dao.VkGroupsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = "classpath:dataAccessContext.xml")
public class VkGroupsDaoIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private VkGroupsDao vkGroupsDao;


}
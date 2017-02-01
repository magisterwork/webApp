package org.ivt.agregator.dao;

import com.vk.api.sdk.objects.groups.GroupFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

@ContextConfiguration(locations = "classpath:dataAccessContext.xml")
public class VkGroupsDaoIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private VkGroupsDao vkGroupsDao;


}
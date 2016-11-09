package org.ivt.agregator.vk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(locations = "classpath:vkTestContext.xml")
public class VkEventsApiTest extends AbstractTestNGSpringContextTests {

    @Autowired
    VkEventsApi eventsApi;
/*
    @Test
    public void shouldGetNotEmptyList() {
        Assert.notEmpty(eventsApi.get());
    }*/
}
package org.ivt.agregator.integration.google.places;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(locations = "classpath:google-photo-test-config.xml")
public class GooglePhotoServiceIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private GooglePhotoService sut;

    public static final int MAX_WIDTH = 807;
    public static final String PHOTO_REFERENCE = "CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU";
    public static final String EXPECTED_URL = "https://lh4.googleusercontent.com/-1wzlVdxiW14/USSFZnhNqxI/AAAAAAAABGw/YpdANqaoGh4/s1600-w648/Google%2BSydney";

    @Test
    public void shouldFindPhotoUrl() {
        String url = sut.getUrl(PHOTO_REFERENCE, MAX_WIDTH);
        assertEquals(url, EXPECTED_URL);
    }
}
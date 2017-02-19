package org.ivt.agregator.filter;

import org.ivt.agregator.integration.culture.entity.CultureMaterial;

public class CultureEventFilterBase implements AddingFilter<CultureMaterial> {

    public static final int MINIMAL_VIEWS = 4;

    @Override
    public boolean shouldAdd(CultureMaterial material) {
        return material.getViews() > MINIMAL_VIEWS;
    }
}

package com.snail.springbootsource.base.entity.dowJones;

import com.snail.springbootsource.base.entity.FXNewsBean;
import com.snail.springbootsource.base.entity.IFXNewsListener;

public class DowJonesNewsListener implements IFXNewsListener {
    @Override
    public String[] getAvailableNewsIds() {
        return new String[0];
    }

    @Override
    public FXNewsBean getNewsByPK(String newsId) {
        return null;
    }

    @Override
    public void postProcessIfNecessary(String newsId) {

    }
}

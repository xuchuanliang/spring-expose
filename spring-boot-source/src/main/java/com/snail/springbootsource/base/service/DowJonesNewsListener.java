package com.snail.springbootsource.base.service;

import com.snail.springbootsource.base.entity.FXNewsBean;
import com.snail.springbootsource.base.entity.IFXNewsListener;

public class DowJonesNewsListener implements IFXNewsListener, PasswordDecodable {

    private String password;

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

    @Override
    public String getEncodedPassword() {
        return this.password;
    }

    @Override
    public void setDecodedPassword(String password) {
        this.password = password;
    }
}

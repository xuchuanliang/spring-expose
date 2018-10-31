package com.snail.springbootsource.base.entity;

import java.util.Objects;

public class FXNewsProvider {
    private IFXNewsListener newsListener;
    private IFXNewsPersister newPersistener;

    public void getAndPersistNews()
    {
        String[] newsIds = newsListener.getAvailableNewsIds();
        if(Objects.isNull(newsIds))
        {
            return;
        }
        for(String newsId : newsIds)
        {
            FXNewsBean newsBean = newsListener.getNewsByPK(newsId);
            newPersistener.persistNews(newsBean);
            newsListener.postProcessIfNecessary(newsId);
        } }

}

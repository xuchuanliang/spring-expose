package com.snail.springbootsource.base.entity;

/**
 * 抓取新闻内容
 */
public interface IFXNewsListener {

    String[] getAvailableNewsIds();

    FXNewsBean getNewsByPK(String newsId);

    void postProcessIfNecessary(String newsId);
}

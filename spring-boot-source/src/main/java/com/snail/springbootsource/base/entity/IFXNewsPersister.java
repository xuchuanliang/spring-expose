package com.snail.springbootsource.base.entity;

/**
 * 存储抓取新闻的内容
 */
public interface IFXNewsPersister {
    void persistNews(FXNewsBean fxNewsBean);
}

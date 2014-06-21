package com.seyren.wx.message.res;

import java.util.List;

/**
 * Created by seyren on 6/14/14.
 */
public class ArticleMessage extends BaseMessage {
    //图文消息数
    private int ArticleCount;
    //多条图文消息
    private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}

package com.seyren.wx.util;

import com.seyren.wx.message.res.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seyren on 6/22/14.
 */
public class SingleArticle {

    public static List<Article> makeSingleArticleWG(){
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setDescription("官方查询安全，方便");
        article.setPicUrl("http://i.gtimg.cn/open/app_icon/00/03/20/32/32032_p1.png");
        article.setUrl("http://wf.nbjj.gov.cn");
        article.setTitle("宁波交通局官方查询网站");
        articleList.add(article);
        return articleList;
    }

    public static List<Article> makeSingleArticleMovie(){
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setDescription("时光网");
        article.setUrl("http://m.mtime.cn/#!/citylist/");
        article.setTitle("时光网");
        article.setPicUrl("http://image.zcool.com.cn/2010/12/74/111/b_1293620104931.jpg");
        articleList.add(article);
        return articleList;
    }
}

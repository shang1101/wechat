package com.seyren.wx.service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.seyren.wx.data.WeathData;
import com.seyren.wx.message.res.Article;
import com.seyren.wx.message.res.ArticleMessage;
import com.seyren.wx.message.res.TextMessage;
import com.seyren.wx.util.MessageUtil;
import com.seyren.wx.util.WeatherUtil;

/**
 * Created by seyren on 6/15/14.
 */
public class CoreService {
    public static String processRequest(HttpServletRequest request) {
        String respXml = null;
        String respContent = null;

        try {
            Map<String , String> requestMap = MessageUtil.parseXml(request);
            String fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            String msgType = requestMap.get("MsgType");

            TextMessage testMessage = new TextMessage();
            testMessage.setToUserName(fromUserName);
            testMessage.setFromUserName(toUserName);
            testMessage.setCreateTime(new Date().getTime());
            testMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);


            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                String content = requestMap.get("Content").trim();
                if (content.endsWith("天气")) {
                    String keyWord = content.replaceAll("天气", "").trim();
                    List<WeathData> weathDatas = WeatherUtil.searchWeath(keyWord);
                    System.out.println(weathDatas.size());
                    System.out.println(weathDatas.get(0).getDayPictureUrl());
                    if (null == weathDatas || 0 == weathDatas.size()) {
                        respContent = String.format("/难过, 您发送的地址未搜索到");
                    } else {
                        List<Article> articleList = WeatherUtil.makeArticleList(weathDatas);
                        System.out.println(articleList);
                        ArticleMessage articleMessage = new ArticleMessage();
                        articleMessage.setToUserName(fromUserName);
                        articleMessage.setFromUserName(toUserName);
                        articleMessage.setCreateTime(new Date().getTime());
                        System.out.println("========> here");
                        articleMessage.setMsgType(MessageUtil.RES_MESSAGE_TYPE_NEWS);
                        articleMessage.setArticles(articleList);
                        articleMessage.setArticleCount(articleList.size());
                        respXml = MessageUtil.messageToXml(articleMessage);
                    }
                }

            }

//            else if (msgType.equals(MessageUtil.RES_MESSAGE_TYPE_IMAGE)) {
//                respContent = "image";
//            }
//
//            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
//                String eventType = requestMap.get("Event");
//
//                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
//                    respContent = "thank you for subscribe";
//                }
//            }
              if (null != respContent) {
                  testMessage.setContent(respContent);
                  respXml = MessageUtil.messageToXml(testMessage);
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }
}

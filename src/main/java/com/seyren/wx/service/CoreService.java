package com.seyren.wx.service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.seyren.wx.data.BaiduPlace;
import com.seyren.wx.data.UserLocation;
import com.seyren.wx.data.WeathData;
import com.seyren.wx.message.res.*;
import com.seyren.wx.util.*;

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
                        articleMessage.setMsgType(MessageUtil.RES_MESSAGE_TYPE_NEWS);
                        articleMessage.setArticles(articleList);
                        articleMessage.setArticleCount(articleList.size());
                        respXml = MessageUtil.messageToXml(articleMessage);
                    }
                } else if (content.equals("附近")) {
                    respContent = getUsage();
                } else if (content.startsWith("附近")) {
                    String keyWord = content.replaceAll("附近", "").trim();
                    UserLocation location = MySqlUtil.getLastLocation(request, fromUserName);
                    if (null == location) {
                        respContent = getUsage();
                    } else {
                        List<BaiduPlace> placeList = BaiduMapUtil.searchPlace(keyWord, location.getBd09Lng(), location.getBd09Lat());
                        if (null == placeList || 0 == placeList.size()) {
                            respContent = String.format("no imformation for this location");
                        } else {
                            List<Article> articleList = BaiduMapUtil.makeArticleList(placeList, location.getBd09Lng(), location.getBd09Lat());
                            ArticleMessage articleMessage = new ArticleMessage();
                            articleMessage.setToUserName(fromUserName);
                            articleMessage.setFromUserName(toUserName);
                            articleMessage.setCreateTime(new Date().getTime());
                            articleMessage.setMsgType(MessageUtil.RES_MESSAGE_TYPE_NEWS);
                            articleMessage.setArticles(articleList);
                            articleMessage.setArticleCount(articleList.size());
                            respXml = MessageUtil.messageToXml(articleMessage);
                        }
                    }
                } else if (content.equals("违规查询")) {
                    ArticleMessage articleMessage = new ArticleMessage();
                    List<Article> articleList = SingleArticle.makeSingleArticleWG();
                    articleMessage.setArticleCount(1);
                    articleMessage.setCreateTime(new Date().getTime());
                    articleMessage.setArticles(articleList);
                    articleMessage.setMsgType(MessageUtil.RES_MESSAGE_TYPE_NEWS);
                    articleMessage.setToUserName(fromUserName);
                    articleMessage.setFromUserName(toUserName);
                    respXml = MessageUtil.messageToXml(articleMessage);
                } else if (content.equals("今日电影")) {
                    ArticleMessage articleMessage = new ArticleMessage();
                    List<Article> articleList = SingleArticle.makeSingleArticleMovie();
                    articleMessage.setArticleCount(1);
                    articleMessage.setCreateTime(new Date().getTime());
                    articleMessage.setArticles(articleList);
                    articleMessage.setMsgType(MessageUtil.RES_MESSAGE_TYPE_NEWS);
                    articleMessage.setToUserName(fromUserName);
                    articleMessage.setFromUserName(toUserName);
                    respXml = MessageUtil.messageToXml(articleMessage);
                } else {
                    respContent = getHelp();
                }

            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                String lng = requestMap.get("Location_Y");
                String lat = requestMap.get("Location_X");
                String bd09Lng = null;
                String bd09Lat = null;
                UserLocation userLocation = BaiduMapUtil.convertCoord(lng, lat);
                if (null != userLocation) {
                    bd09Lng = userLocation.getBd09Lng();
                    bd09Lat = userLocation.getBd09Lat();
                }
                MySqlUtil.saveUserLocation(request, fromUserName, lng, lat, bd09Lng, bd09Lat);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("successfull");
                respContent = stringBuffer.toString();
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = getSubscribeMsg();
                }
            } else {
                respContent = getHelp();
            }
            if (null != respContent) {
              testMessage.setContent(respContent);
              respXml = MessageUtil.messageToXml(testMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }


    private static String getSubscribeMsg() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("欢迎订阅奉化生活 by Seyren").append("\n");
        buffer.append("有些功能还在完善中 比如周边查询功能").append("\n");
        buffer.append("使用说明").append("\n\n");
        buffer.append("1>发送'附近'查看周边搜索功能").append("\n");
        buffer.append("2>发送'今日电影'查看电影资讯").append("\n");
        buffer.append("3>发送'违规查询'查询车辆违规情况").append("\n");
        buffer.append("4>发送'地名+天气' 查询天气讯息 \n例如：发送'奉化天气'");
        return buffer.toString();
    }

    private static String getUsage() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("周边搜索使用说明").append("\n\n");
        buffer.append("1>发送地理位置").append("\n");
        buffer.append("点击窗口底部的'+'按钮，选择'位置'，点发送").append("\n");
        buffer.append("2>指定关键词搜索").append("\n");
        buffer.append("格式：附近+关键词\n例如：附近ATM 附近KTV 附近WC");
        return buffer.toString();
    }

    private static String getHelp() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("使用说明").append("\n\n");
        buffer.append("1>发送'附近'查看周边搜索功能").append("\n");
        buffer.append("2>发送'今日电影'查看电影资讯").append("\n");
        buffer.append("3>发送'违规查询'查询车辆违规情况").append("\n");
        buffer.append("4>发送'地名+天气' 查询天气讯息 \n例如：发送'奉化天气'");
        return buffer.toString();
    }

}

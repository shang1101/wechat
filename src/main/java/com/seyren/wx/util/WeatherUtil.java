package com.seyren.wx.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.BufferPoolMXBean;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.javafx.jmx.json.JSONDocument;
import com.seyren.wx.message.res.Article;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.seyren.wx.data.WeathData;

/**
 * Created by seyren on 6/15/14.
 */
public class WeatherUtil {

    public static String httpRequest(String requestUrl) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");


            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    public static List<WeathData> searchWeath(String query) throws Exception {
        String requestUrl = "http://api.map.baidu.com/telematics/v3/weather?location=LOCATION&output=json&ak=2f8a8f64772ea877b5a19367b05138a5";
        //location用真实地址代替,注意编码
        requestUrl = requestUrl.replace("LOCATION", URLEncoder.encode(query, "UTF-8"));
        String respJson = httpRequest(requestUrl);
        System.out.println(respJson);
        List<WeathData> weathDatas = parseWeathJson(respJson);
        return weathDatas;
    }


    public static List<WeathData> parseWeathJson(String json) {
        List<WeathData> weathDatalist = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            //String result = jsonObject.getString("error");
            //if (result.equals("0")) {
                JSONArray jsonData = jsonObject.getJSONArray("results");
                JSONObject details = jsonData.getJSONObject(0);
                String cityName = details.getString("currentCity");
                JSONArray jsonArray = details.getJSONArray("weather_data");
                weathDatalist = new ArrayList<WeathData>();
                //迭代所有元素
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject data = (JSONObject) jsonArray.get(i);
                    WeathData weathData = new WeathData();
                    weathData.setCityName(cityName);
                    weathData.setData(data.getString("date"));
                    weathData.setDayPictureUrl(data.getString("dayPictureUrl"));
                    weathData.setNightPictureUrl(data.getString("nightPictureUrl"));
                    weathData.setWeather(data.getString("weather"));
                    weathData.setTemperature(data.getString("temperature"));
                    weathData.setWind(data.getString("wind"));
                    weathDatalist.add(weathData);
                }
            //} else {
            //    return new ArrayList<WeathData>();
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weathDatalist;
    }

    public static List<Article> makeArticleList(List<WeathData> weathDatalist) {
        List<Article> articleList = new ArrayList<Article>();
        WeathData weathData = null;
        for (int i = 0; i < weathDatalist.size(); i++) {
            weathData = weathDatalist.get(i);
            Article article = new Article();
            article.setTitle(weathData.getData() + weathData.getCityName() + weathData.getWind() + weathData.getTemperature() +weathData.getWeather());
            article.setUrl("");
            article.setPicUrl(weathData.getDayPictureUrl());
            articleList.add(article);
        }
        return articleList;
    }











}





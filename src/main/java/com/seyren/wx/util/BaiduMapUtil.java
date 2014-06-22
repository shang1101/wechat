package com.seyren.wx.util;

import it.sauronsoftware.base64.Base64;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.seyren.wx.message.res.Article;
import com.seyren.wx.data.BaiduPlace;
import com.seyren.wx.data.UserLocation;


public class BaiduMapUtil {

	public static List<BaiduPlace> searchPlace(String query, String lng, String lat) throws Exception {
		String requestUrl = "http://api.map.baidu.com/place/v2/search?&query=QUERY&location=LAT,LNG&radius=2000&output=xml&scope=2&page_size=10&page_num=0&ak=2f8a8f64772ea877b5a19367b05138a5";
		requestUrl = requestUrl.replace("QUERY", URLEncoder.encode(query, "UTF-8"));
		requestUrl = requestUrl.replace("LAT", lat);
		requestUrl = requestUrl.replace("LNG", lng);
		String respXml = httpRequest(requestUrl);
		List<BaiduPlace> placeList = parsePlaceXml(respXml);
		return placeList;
	}


	public static String httpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();


			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();

			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}


	@SuppressWarnings("unchecked")
	private static List<BaiduPlace> parsePlaceXml(String xml) {
		List<BaiduPlace> placeList = null;
		try {
			Document document = DocumentHelper.parseText(xml);

			Element root = document.getRootElement();

			Element resultsElement = root.element("results");

			List<Element> resultElementList = resultsElement.elements("result");

			if (resultElementList.size() > 0) {
				placeList = new ArrayList<BaiduPlace>();

				Element nameElement = null;

				Element addressElement = null;

				Element locationElement = null;

				Element telephoneElement = null;

				Element detailInfoElement = null;

				Element distanceElement = null;

				for (Element resultElement : resultElementList) {
					nameElement = resultElement.element("name");
					addressElement = resultElement.element("address");
					locationElement = resultElement.element("location");
					telephoneElement = resultElement.element("telephone");
					detailInfoElement = resultElement.element("detail_info");

					BaiduPlace place = new BaiduPlace();
					place.setName(nameElement.getText());
					place.setAddress(addressElement.getText());
					place.setLng(locationElement.element("lng").getText());
					place.setLat(locationElement.element("lat").getText());
					if (null != telephoneElement)
						place.setTelephone(telephoneElement.getText());
					if (null != detailInfoElement) {
						distanceElement = detailInfoElement.element("distance");
						if (null != distanceElement)
							place.setDistance(Integer.parseInt(distanceElement.getText()));
					}
					placeList.add(place);
				}

				Collections.sort(placeList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return placeList;
	}


	public static List<Article> makeArticleList(List<BaiduPlace> placeList, String bd09Lng, String bd09Lat) {

		String basePath = "http://seyren.duapp.com/";
		List<Article> list = new ArrayList<Article>();
		BaiduPlace place = null;
		for (int i = 0; i < placeList.size(); i++) {
			place = placeList.get(i);
			Article article = new Article();
			article.setTitle(place.getName() + "\n距离" + place.getDistance() + "米");

			article.setUrl(String.format(basePath + "route.jsp?p1=%s,%s&p2=%s,%s", bd09Lng, bd09Lat, place.getLng(), place.getLat()));

			if (i == 0)
				article.setPicUrl(basePath + "images/poisearch.png");
			else
				article.setPicUrl(basePath + "images/navi.png");
			list.add(article);
		}
		return list;
	}


	public static UserLocation convertCoord(String lng, String lat) {

		String convertUrl = "http://api.map.baidu.com/ag/coord/convert?from=2&to=4&x={x}&y={y}";
		convertUrl = convertUrl.replace("{x}", lng);
		convertUrl = convertUrl.replace("{y}", lat);

		UserLocation location = new UserLocation();
		try {
			String jsonCoord = httpRequest(convertUrl);
			JSONObject jsonObject = new JSONObject(jsonCoord);

			location.setBd09Lng(Base64.decode(jsonObject.getString("x"), "UTF-8"));
			location.setBd09Lat(Base64.decode(jsonObject.getString("y"), "UTF-8"));
		} catch (Exception e) {
			location = null;
			e.printStackTrace();
		}
		return location;
	}
}

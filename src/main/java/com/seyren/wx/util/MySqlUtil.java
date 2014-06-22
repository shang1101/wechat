package com.seyren.wx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
//import com.baidu.bae.api.util.BaeEnv;

import com.seyren.wx.data.UserLocation;







/**
 * Created by seyren on 6/22/14.
 */
public class MySqlUtil {
    private Connection getConn(HttpServletRequest request) {
        Connection conn = null;

        //String host = request.getHeader(BaeEnv.BAE_ENV_ADDR_SQL_IP);
        String host = "sqld.duapp.com";
        //String port = request.getHeader(BaeEnv.BAE_ENV_ADDR_SQL_PORT);
        String port = "4050";
        //String username = request.getHeader(BaeEnv.BAE_ENV_AK);
        String username = "Z8sY5UCTejsvlVY4Q25LNR5c";
        //String password = request.getHeader(BaeEnv.BAE_ENV_SK);
        String password = "Yu0x3nD9q71VseyUGAxrv0weKGAZEZdC";
        String dbName = "MYBGwJKngxrClFaHtAnJ";
        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    public static void saveUserLocation(HttpServletRequest request, String openId, String lng, String lat, String bd09_lng, String bd09_lat) {
        String sql = "insert into user_location(open_id, lng, lat, bd09_lng, bd09_lat) values (?,?,?,?,?)";
        try {
            Connection conn = new MySqlUtil().getConn(request);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, openId);
            ps.setString(2, lng);
            ps.setString(3, lat);
            ps.setString(4, bd09_lng);
            ps.setString(5, bd09_lat);
            ps.executeUpdate();
            //free resource
            ps.close();
            conn.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * last location
     */
    public static UserLocation getLastLocation(HttpServletRequest request, String openId) {
        UserLocation userLocation = null;
        String sql = "select open_id, lng, lat, bd09_lng, bd09_lat from user_location where open_id=? order by id desc limit 0,1";
        try {
            Connection conn = new MySqlUtil().getConn(request);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, openId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userLocation = new UserLocation();
                userLocation.setOpenId(rs.getString("open_id"));
                userLocation.setLng(rs.getString("lng"));
                userLocation.setLat(rs.getString("lat"));
                userLocation.setBd09Lng(rs.getString("bd09_lng"));
                userLocation.setBd09Lat(rs.getString("bd09_lat"));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userLocation;
    }

}



package com.seyren.wx.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.seyren.wx.service.CoreService;
import com.seyren.wx.util.SignUtil;



/**
 * Created by seyren on 6/13/14.
 * 请求核心类
 */
public class CoreServlet extends HttpServlet {


    /**
     * 处理微信服务器发来的消息
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // todo 消息接收 处理 响应
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String signature = request.getParameter("signature");
////       时间戳
        String timestamp = request.getParameter("timestamp");
////      随机数
        String nonce = request.getParameter("nonce");

        PrintWriter out = response.getWriter();

        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            String respXml = CoreService.processRequest(request);
            out.print(respXml);
        }
        out.close();
        out = null;


    }


    /**
     *
     * 请求校验(from tencent server)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //       微型加密签名
        String signature = request.getParameter("signature");
//       时间戳
        String timestamp = request.getParameter("timestamp");
//      随机数
        String nonce = request.getParameter("nonce");
//        随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }
}

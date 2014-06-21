package com.seyren.wx.message.res;


/**
 * Created by seyren on 6/14/14.
 * 文本消息
 * 继承消息基类
 */
public class TextMessage extends BaseMessage {
    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


}

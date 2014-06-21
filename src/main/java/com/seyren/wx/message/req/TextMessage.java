package com.seyren.wx.message.req;

/**
 * Created by seyren on 6/14/14.
 * 文本消息
 * 继承消息基类
 */
public class TextMessage extends BaseMessage {
    // 消息内容
    private String _Content;

    public String get_Content() {
        return _Content;
    }

    public void set_Content(String Content) {
        _Content = Content;
    }


}

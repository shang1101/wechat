package com.seyren.wx.message.req;

/**
 * Created by seyren on 6/14/14.
 * 图文基类 继承消息基类
 */
public class ImgMessage extends BaseMessage {
    // 图片链接
    private String _ImgUrl;

    public String get_ImgUrl() {
        return _ImgUrl;
    }

    public void set_ImgUrl(String ImgUrl) {
        _ImgUrl = ImgUrl;
    }
}

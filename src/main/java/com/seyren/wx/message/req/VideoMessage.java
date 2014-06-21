package com.seyren.wx.message.req;

/**
 * Created by seyren on 6/14/14.
 * 视频消息基类
 */
public class VideoMessage extends BaseMessage {
    //视频消息ID
    private String MediaId;
    //视频消息略缩图媒体ID
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}

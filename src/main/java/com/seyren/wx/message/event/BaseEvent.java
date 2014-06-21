package com.seyren.wx.message.event;

/**
 * Created by seyren on 6/14/14.
 * 消息基类
 */
public class BaseEvent {
    // 开发者微信号
    private String ToUserName;
    // 发送者微信号（openid）
    private String FromUserName;
    // 消息创建时间（long整型）
    private long CreateTime;
    // 消息类型
    private String MsgType;
    // 事件类型
    private String Event;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUsername) {
        FromUserName = fromUsername;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }
}

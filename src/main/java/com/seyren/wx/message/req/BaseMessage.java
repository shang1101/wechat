package com.seyren.wx.message.req;

/**
 * Created by seyren on 6/14/14.
 * 消息基类设计
 *
 * 属性：ToUserName FromUserName CreateTime MsgType MsgId
 * 方法：getToUserName setToUserName ......
 */
public class BaseMessage {
    // 开发者微信号
    private String ToUserName;
    // 发送者微信号（openid）
    private String FromUserName;
    // 消息创建时间（long整型）
    private long CreateTime;
    // 消息类型
    private String MsgType;
    // 消息ID 64位整型
    private long MsgId;

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

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }
}

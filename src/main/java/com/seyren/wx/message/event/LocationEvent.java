package com.seyren.wx.message.event;

/**
 * Created by seyren on 6/14/14.
 * 地理位置 事件
 */
public class LocationEvent extends BaseEvent {
    // 地理位置纬度
    private String WeiDu;
    // 地理位置经度
    private String JinDu;
    // 地理位置经度
    private String JingDu;

    public String getWeiDu() {
        return WeiDu;
    }

    public void setWeiDu(String weiDu) {
        WeiDu = weiDu;
    }

    public String getJinDu() {
        return JinDu;
    }

    public void setJinDu(String jinDu) {
        JinDu = jinDu;
    }

    public String getJingDu() {
        return JingDu;
    }

    public void setJingDu(String jingDu) {
        JingDu = jingDu;
    }
}

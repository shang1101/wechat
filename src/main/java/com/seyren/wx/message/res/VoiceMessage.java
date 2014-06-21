package com.seyren.wx.message.res;

/**
 * Created by seyren on 6/14/14.
 * 语音消息
 */
public class VoiceMessage extends BaseMessage {
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }
}

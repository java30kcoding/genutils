package cn.itlou.genutils.wechat;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 微信公众号推送工具类
 *
 * @author yuanyl
 * @date 2020/6/3 17:46
 **/
@Component
@Slf4j
public class WeChatUtil {

    private WeChatUtil(){}

    public void push(String value) {
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId("x");
        wxStorage.setSecret("x");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        //2,推送消息
        List<WxMpTemplateData> data = Arrays.asList(new WxMpTemplateData("count", value));
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("x")//要推送的用户openid
//                .toUser("oBzWI6d9Szd_o1YrHyGpHo5lXbAk")//要推送的用户openid
                .data(data)
                .templateId("x-zodR4w")//模版id
                .build();
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        //        templateMessage.addData(new WxMpTemplateData("name", "value", "#FF00FF"));
        //                templateMessage.addData(new WxMpTemplateData(name2, value2, color2));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }

    }

}

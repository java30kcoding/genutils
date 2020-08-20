package cn.itlou.genutils.controller;

import cn.itlou.genutils.mail.MailUtil;
import cn.itlou.genutils.tabledoc.TableDescGenService;
import cn.itlou.genutils.pg.TableDescMapper;
import cn.itlou.genutils.tableexcel.ExcelService;
import cn.itlou.genutils.wechat.WeChatUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyl
 * @date 2020/5/8 21:53
 **/
@RestController
public class TestController {

    @Resource
    TableDescMapper tableDescMapper;
    @Resource
    TableDescGenService tableDescGenService;
    @Resource
    MailUtil mailUtil;
    @Resource
    WeChatUtil weChatUtil;
    @Resource
    ExcelService excelService;

    @GetMapping("testD")
    public void testD(){
        System.out.println("a");
//        excelService.genAll();
    }

    @GetMapping("/test")
    public void test() throws IOException {
        Map<String, Object> hashMap = Maps.newHashMap();
//        List<String> tableName = tableDescMapper.getAllTableName();
        List<String> tableName = Arrays.asList("aaa");
//        List<String> tableName = Arrays.asList("t_rf_record_history", "t_sw_config_unified_dictionaries");
        List<String> realName = Lists.newArrayList();
        for (String name : tableName) {
            hashMap.put("tableList" + tableName.indexOf(name), tableDescGenService.gen(name));
//            realName.add()
            hashMap.put("tableName" + tableName.indexOf(name), name);
        }
        tableDescGenService.genBig(hashMap);

    }

    @GetMapping("genOne")
    public void genOne() throws IOException{
//                t_sff_yjfp
        tableDescGenService.gen("t_sff_yjfp");
    }

    @GetMapping("genAll")
    public void genAll() throws IOException {
        tableDescGenService.genAllComplete();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 104; i++) {
            System.out.println("{{+tableList"+ i + "}}");
        }
    }

//    @Scheduled(cron = "0/5 * * * * ?")
//    public void test1(){
//        testGet();
//    }

    @GetMapping("/testGet")
    public void testGet(){
        String url = "http://remotelab.top/runzhong/getOnlineDevices?courseDid=10002";
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> entity = template.getForEntity(url, String.class);
        String body = entity.getBody();
        JSONArray result = JSON.parseArray(body);
        int haveGroup = 0;
        for (Object o : result) {
            JSONObject jsonObject = (JSONObject) o;
            if (jsonObject.get("user") == null) {
                haveGroup++;
            }
        }
        System.out.println(haveGroup);
//        MailDto mailDto = new MailDto();
//        mailDto.setRecipient("434356430@qq.com");
//        mailDto.setSubject("有机器啦~目前空闲机器为" + haveGroup + "台！");
//        mailDto.setContent("颜晓庆快来做实验吧~");
        if (haveGroup > 2) {
            weChatUtil.push(haveGroup + "");
        }
    }

}

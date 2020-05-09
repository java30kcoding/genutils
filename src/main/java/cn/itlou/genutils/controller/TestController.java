package cn.itlou.genutils.controller;

import cn.itlou.genutils.model.TableDescModel;
import cn.itlou.genutils.tabledoc.TableDescGenService;
import cn.itlou.genutils.tabledoc.TableDescMapper;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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

    @GetMapping("/test")
    public void test() throws IOException {
        Map<String, Object> hashMap = Maps.newHashMap();
        List<String> tableName = tableDescMapper.getAllTableName();
//        List<String> tableName = Arrays.asList("t_rf_record_history", "t_sw_config_unified_dictionaries");
        for (String name : tableName) {
            hashMap.put("tableList" + tableName.indexOf(name), tableDescGenService.gen(name));
//            hashMap.put("tableName" + tableName.indexOf(name), name);
        }
        tableDescGenService.genBig(hashMap);

    }

    public static void main(String[] args) {
        for (int i = 0; i < 104; i++) {
            System.out.println("{{+tableList"+ i + "}}");
        }
    }

}

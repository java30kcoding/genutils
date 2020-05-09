package cn.itlou.genutils.tabledoc;

import cn.itlou.genutils.model.TableDescModel;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据表结构在word中填充数据
 *
 * @author yuanyl
 * @date 2020/5/8 19:19
 **/
@RestController
public class TableDescriptionGenMain {

    public void gen(){

    }

    public static void main(String[] args) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", "fuckTable");
        data.put("tableDescription", "测试数据");
        List<TableDescModel> table = Lists.newArrayList();
        TableDescModel tableDescModel = new TableDescModel();
        tableDescModel.setTableName("testTable");
        tableDescModel.setTableDesc("测试表含义");
        tableDescModel.setFieldName("year");
        tableDescModel.setFieldType("varchar(255)");
        tableDescModel.setFieldDesc("年度");
        tableDescModel.setCanNull("否");
        TableDescModel tabledesc2 = new TableDescModel();
        tabledesc2.setTableName("testTable");
        tabledesc2.setTableDesc("测试表含义");
        tabledesc2.setFieldName("year");
        tabledesc2.setFieldType("varchar(255)");
        tabledesc2.setFieldDesc("年度");
        tabledesc2.setCanNull("否");
        table.add(tabledesc2);
        table.add(tableDescModel);
        DetailData detailTable = new DetailData();

//        Configure config = Configure.newBuilder().bind("table", new DetailTablePolicy()).build();
//        XWPFTemplate template = XWPFTemplate.compile(new File("D:\\test.docx"), config).render(
//            new HashMap<String, Object>() {{
//                put("table", table);
//            }}
//        );
        List<Map<String, Object>> t = Lists.newArrayList();
        Map<String, Object> tmp = Maps.newHashMap();
        tmp.put("table", table);
        t.add(tmp);
        t.add(tmp);
        HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
        Configure config = Configure.newBuilder().bind("table", policy).build();
        XWPFTemplate template = XWPFTemplate.compile(new File("D:\\test.docx"), config).render(
                t
        );

        try {
            template.writeToFile("D:\\output.docx");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            template.close();
        }

    }

}

package cn.itlou.genutils.tabledoc;

import cn.itlou.genutils.model.TableDescModel;
import cn.itlou.genutils.pg.TableDescMapper;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyl
 * @date 2020/5/8 22:21
 **/
@Service
public class TableDescGenService {

    @Resource
    TableDescMapper tableDescMapper;

    public void genAllComplete() throws IOException {
        List<String> tableNames = tableDescMapper.getAllTableName();
        TableData data = new TableData();
        List<TableInfo> tableInfos = Lists.newArrayList();
        for (String tableName : tableNames) {
            TableInfo info = new TableInfo();
            List<TableDescModel> tableInfo = tableDescMapper.getTableInfo(tableName);
            for (TableDescModel tableDescModel : tableInfo) {
                tableDescModel.setChinese(tableDescModel.getFieldDesc());
                tableDescModel.setCanNull(tableDescModel.getCanNull().equals("t") ? "否" : "是");
            }
            info.setIndex(tableNames.indexOf(tableName));
            info.setItem(new TableItem(tableInfo.get(0).getTableName(), tableInfo.get(0).getTableDesc()));
            info.setColItem(tableInfo);
            tableInfos.add(info);
        }
        data.setTableInfoList(tableInfos);
//        info.setIndex(1);
//        info.setItem(new TableItem("abc", "def"));
//        info.setColItem1(new ColItem("a", "b", "c"));
//        tableInfos.add(info);

//        info = new TableInfo();
//        info.setIndex(2);
//        info.setItem(new TableItem("test", "test"));
//        info.setColItem1(new ColItem("d", "e", "f"));
//        info.setColItem(colItems);
//        tableInfos.add(info);
//        data.setTableInfoList(tableInfos);

        HackLoopTableRenderPolicy hackLoopTableRenderPolicy = new HackLoopTableRenderPolicy();
        Configure config = Configure.newBuilder().bind("colItem", hackLoopTableRenderPolicy).build();

        XWPFTemplate template = XWPFTemplate.compile("D:\\template.docx", config).render(data);
        try {
            template.writeToFile("D:\\OKR_OUT.docx");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            template.close();
        }
    }

    public DocxRenderData gen(String tableName) throws IOException {
        List<TableDescModel> tableInfo = tableDescMapper.getTableInfo(tableName);
        for (TableDescModel tableDescModel : tableInfo) {
            tableDescModel.setChinese(tableDescModel.getFieldDesc());
            tableDescModel.setCanNull(tableDescModel.getCanNull().equals("t") ? "否" : "是");
        }
        HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
        Configure config = Configure.newBuilder().bind("table", policy).build();
        XWPFTemplate template = XWPFTemplate.compile(new File("D:\\table.docx"), config).render(
                new HashMap<String, Object>() {{
                    put("table", tableInfo);
                }}
        );
        try {
            template.writeToFile("D:\\output.docx");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            template.close();
        }
        DocxRenderData docxRenderData = new DocxRenderData(new File("D:\\output.docx"));
        return docxRenderData;
    }

    public void genBig(Map<String, Object> map) throws IOException {
        XWPFTemplate template = XWPFTemplate.compile(new File("D:\\gloabl.docx")).render(
                map
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

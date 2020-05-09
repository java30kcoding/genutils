package cn.itlou.genutils.tabledoc;

import cn.itlou.genutils.model.TableDescModel;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
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

    private Map hashMap;

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

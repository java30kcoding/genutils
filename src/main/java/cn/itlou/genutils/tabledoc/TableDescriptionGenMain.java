package cn.itlou.genutils.tabledoc;

import cn.itlou.genutils.model.TableDescModel;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.BookmarkRenderPolicy;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.RestController;

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
//        Map<String, Object> data = new HashMap<>();
//        data.put("tableName", "fuckTable");
//        data.put("tableDescription", "测试数据");
//        List<TableDescModel> table = Lists.newArrayList();
//        TableDescModel tableDescModel = new TableDescModel();
//        tableDescModel.setTableName("testTable");
//        tableDescModel.setTableDesc("测试表含义");
//        tableDescModel.setFieldName("year");
//        tableDescModel.setFieldType("varchar(255)");
//        tableDescModel.setFieldDesc("年度");
//        tableDescModel.setCanNull("否");
//        TableDescModel tabledesc2 = new TableDescModel();
//        tabledesc2.setTableName("testTable");
//        tabledesc2.setTableDesc("测试表含义");
//        tabledesc2.setFieldName("year");
//        tabledesc2.setFieldType("varchar(255)");
//        tabledesc2.setFieldDesc("年度");
//        tabledesc2.setCanNull("否");
//        table.add(tabledesc2);
//        table.add(tableDescModel);
//        DetailData detailTable = new DetailData();

//        Configure config = Configure.newBuilder().bind("table", new DetailTablePolicy()).build();
//        XWPFTemplate template = XWPFTemplate.compile(new File("D:\\test.docx"), config).render(
//            new HashMap<String, Object>() {{
//                put("table", table);
//            }}
//        );
//        List<Map<String, Object>> t = Lists.newArrayList();
//        Map<String, Object> tmp = Maps.newHashMap();
//        tmp.put("table", table);
//        t.add(tmp);
//        t.add(tmp);
//        HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
//        Configure config = Configure.newBuilder().bind("table", policy).build();
//        XWPFTemplate template = XWPFTemplate.compile(new File("D:\\test.docx"), config).render(
//                t
//        );

//        try {
//            template.writeToFile("D:\\output.docx");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            template.close();
//        }

        TableData data = new TableData();
        List<TableInfo> tableInfos = Lists.newArrayList();
        List<ColItem> colItems = Arrays.asList(new ColItem("a", "b", "c"),
                new ColItem("d", "e", "f"));
        TableInfo info = new TableInfo();
        info.setIndex(1);
        info.setItem(new TableItem("abc", "def"));
//        info.setColItem(colItems);
//        info.setColItem1(new ColItem("a", "b", "c"));
        tableInfos.add(info);

        info = new TableInfo();
        info.setIndex(2);
        info.setItem(new TableItem("test", "test"));
//        info.setColItem1(new ColItem("d", "e", "f"));
//        info.setColItem(colItems);
        tableInfos.add(info);
        data.setTableInfoList(tableInfos);

        HackLoopTableRenderPolicy hackLoopTableRenderPolicy = new HackLoopTableRenderPolicy();
        Configure config = Configure.newBuilder().bind("colItem", hackLoopTableRenderPolicy).build();

        XWPFTemplate template = XWPFTemplate.compile("D:\\template.docx", config).render(data);
        template.writeToFile("D:\\OKR_OUT.docx");

//        List<OKRItem> objectives = new ArrayList<OKRItem>();
//        OKRItem item = new OKRItem();
//        item.setIndex(1);
//        item.setObject(new Objective("打造一个空前繁荣、强大的百度移动生态", "4%"));
//        item.setKr1(new KeyResult("恪守安全可控、引人向上、忠诚服务、降低门槛的产品价值观，持续优化用户体验，提升百度系产品的总时长份额", "5%"));
//        item.setKr2(new KeyResult("恪守良币驱逐劣币的商业价值观，实现在爱惜品牌口碑、优化用户体验基础上的收入增长", "1%"));
//        item.setKr3(new KeyResult("产品要有创新，不能总是me too，me later", "1%"));
//        objectives.add(item);
//
//        item = new OKRItem();
//        item.setIndex(2);
//        item.setObject(new Objective("主流AI赛道模式跑通，实现可持续增长", "10%"));
//        item.setKr1(new KeyResult("小度小度进入千家万户", "15%"));
//        item.setKr2(new KeyResult("智能驾驶、智能交通找到规模化发展路径", "0%"));
//        item.setKr3(new KeyResult("云及AI2B业务至少在个万亿级行业成为第一", "1%"));
//        objectives.add(item);
//        data.setObjectives(objectives);
//
//        List<OKRItem> manageObjectives = new ArrayList<OKRItem>();
//        item = new OKRItem();
//        item.setIndex(1);
//        item.setObject(new Objective("提升百度的组织能力，有效支撑住业务规模的高速增长，不拖战略的后腿", "1%"));
//        item.setKr1(new KeyResult("全公司成功推行OKR制度，有效降低沟通协调成本，激励大家为更高目标奋斗取得比KPI管理更好的业绩", "1%"));
//        item.setKr2(new KeyResult("激发从ESTAFF到一线员工的主人翁意识，使之比2018年更有意愿有能力自我驱动管理好各自负责的领域", "0%"));
//        item.setKr3(new KeyResult("建立合理的管理人员新陈代谢机制，打造出不少于2名业界公认的优秀领军人物", "1%"));
//        manageObjectives.add(item);
//        data.setManageObjectives(manageObjectives);
//
//        data.setDate("2020-01-31");
//
//        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/okr/OKR.docx").render(data);
//        template.writeToFile("out_example_okr.docx");

    }

}

package cn.itlou.genutils.tabledoc;

import cn.itlou.genutils.model.TableDescModel;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.List;

/**
 * @author yuanyl
 * @date 2020/5/8 20:26
 **/
public class DetailTablePolicy extends DynamicTableRenderPolicy {

    // 表名称所在行数
    int tableStartRow = 0;
    // 功能说明所在行数
    int descStartRow = 1;
    // 字段详情
    int fieldDescStartRow = 3;

    @Override
    public void render(XWPFTable table, Object data) {
        List<TableDescModel> tableDescList = (List<TableDescModel>) data;
        TextRenderData tableName = new TextRenderData("fuckTable");
        CellRenderData tableNameData = new CellRenderData(tableName);
        TextRenderData tableDesc = new TextRenderData("测试数据");
        CellRenderData tableDescData = new CellRenderData(tableDesc);
        MiniTableRenderPolicy.Helper.renderCell(table.getRow(tableStartRow).getTableCells().get(1), tableNameData, null);
        MiniTableRenderPolicy.Helper.renderCell(table.getRow(descStartRow).getTableCells().get(1), tableDescData, null);

        // 货品明细
        if (null != tableDescList) {
            table.removeRow(fieldDescStartRow);
            for (int i = 0; i < tableDescList.size(); i++) {
                XWPFTableRow insertNewTableRow = table.insertNewTableRow(fieldDescStartRow);
                for (int j = 0; j < 6; j++) insertNewTableRow.createCell();
                // 渲染单行货品明细数据
                new RowRenderData().build(tableDescList.get(i).getFieldName());
                MiniTableRenderPolicy.Helper.renderRow(table, 3, new RowRenderData().build(tableDescList.get(i).getFieldName()));
            }
        }
    }
}
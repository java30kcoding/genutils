package cn.itlou.genutils.tabledoc;

import cn.itlou.genutils.model.TableDescModel;
import lombok.Data;

import java.util.List;

@Data
public class TableData {

    private List<TableInfo> tableInfoList;
    private List<TableInfo> tableColList;


}

@Data
class TableInfo {
    int index;
    TableItem item;
//    ColItem colItem1;
//    ColItem colItem2;
    List<TableDescModel> colItem;
}

@Data
class TableItem {
    private String tableName;
    private String tableDesc;
    public TableItem(String tableName, String tableDesc) {
        this.tableName = tableName;
        this.tableDesc = tableDesc;
    }
}

@Data
class ColItem {
    private String tableName;
    private String tableDesc;
    private String fieldName;
    private String chinese;
    private String fieldType;
    private String fieldDesc;
    private String canNull;
    public ColItem(String fieldName, String chinese, String fieldDesc) {
        this.fieldName = fieldName;
        this.chinese = chinese;
        this.fieldDesc = fieldDesc;
    }
}

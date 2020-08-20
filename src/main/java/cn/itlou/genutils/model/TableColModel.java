package cn.itlou.genutils.model;

import lombok.Data;

/**
 * @author yuanyl
 * @date 2020/7/28 16:02
 **/
@Data
public class TableColModel {

    private String tableName;
    private String oldCol;
    private String oldColType;
    private String newCol;
    private String newColType;
    private String remark;

}

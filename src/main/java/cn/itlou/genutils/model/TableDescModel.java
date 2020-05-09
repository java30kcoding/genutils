package cn.itlou.genutils.model;

import lombok.Data;

/**
 * 数据库表结构
 *
 * @author yuanyl
 * @date 2020/5/8 20:11
 **/
@Data
public class TableDescModel {

    private String tableName;
    private String tableDesc;
    private String fieldName;
    private String chinese;
    private String fieldType;
    private String fieldDesc;
    private String canNull;

}

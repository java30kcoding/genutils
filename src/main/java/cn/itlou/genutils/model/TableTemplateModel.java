package cn.itlou.genutils.model;

import lombok.Data;

import java.util.List;

/**
 * @author yuanyl
 * @date 2020/7/28 17:23
 **/
@Data
public class TableTemplateModel {

    private List<String> oldCol;
    private List<String> oldColType;
    private List<String> newCol;
    private List<String> newColType;
    private List<String> remark;

}

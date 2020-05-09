package cn.itlou.genutils.tabledoc;

import com.deepoove.poi.data.RowRenderData;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyl
 * @date 2020/5/8 21:13
 **/
@Data
public class DetailData {

    // 表结构数据
    private List<RowRenderData> tableDataList;

}

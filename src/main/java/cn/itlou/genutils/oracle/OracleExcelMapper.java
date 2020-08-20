package cn.itlou.genutils.oracle;

import cn.itlou.genutils.model.TableColModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yuanyl
 * @date 2020/7/28 17:00
 **/
@Mapper
public interface OracleExcelMapper {

    @Select("SELECT T.COLUMN_NAME as oldCol,\n" +
            "       T.DATA_TYPE as oldColType,\n" +
            "       T.DATA_LENGTH,\n" +
            "       C.COMMENTS as remark\n" +
            "FROM USER_TAB_COLUMNS T,\n" +
            "     USER_COL_COMMENTS C\n" +
            "WHERE T.TABLE_NAME = C.TABLE_NAME\n" +
            "  AND T.COLUMN_NAME = C.COLUMN_NAME\n" +
            "  AND T.TABLE_NAME = UPPER( #{tableName, jdbcType = VARCHAR} )")
    List<TableColModel> getTableInfo(String tableName);

}

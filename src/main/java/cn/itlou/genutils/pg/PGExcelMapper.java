package cn.itlou.genutils.pg;

import cn.itlou.genutils.model.TableColModel;
import cn.itlou.genutils.model.TableDescModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yuanyl
 * @date 2020/7/28 16:12
 **/
@Mapper
public interface PGExcelMapper {

    @Select("select a.attnum AS id,\n" +
            "c.relname AS tableName,\n" +
            "-- 这里是表描述,原本新建数据库的时候没有添加表描述,查询出来会为空,注释掉就好,有表描述的放开这条注释\n" +
            "-- cast(obj_description(relfilenode,'pg_class') as varchar) AS tableDesc,\n" +
            "a.attname AS newCol,\n" +
            "concat_ws('',t.typname,SUBSTRING(format_type(a.atttypid,a.atttypmod) from '\\(.*\\)')) as newColType,\n" +
            "b.description AS remark\n" +
            "from pg_class c, pg_attribute a LEFT OUTER JOIN pg_description b ON a.attrelid=b.objoid AND a.attnum = b.objsubid,\n" +
            "pg_type t\n" +
            "-- 这里是你的表名\n" +
            "where c.relname = #{tableName, mode = IN, jdbcType = VARCHAR}\n" +
            "and a.attnum > 0\n" +
            "and a.attrelid = c.oid\n" +
            "and a.atttypid = t.oid\n" +
            "and relnamespace = (SELECT oid FROM pg_namespace WHERE nspname='zsxt')\n" +
            "ORDER BY c.relname DESC,a.attnum ASC")
    List<TableColModel> getTableInfo(String tableName);

}

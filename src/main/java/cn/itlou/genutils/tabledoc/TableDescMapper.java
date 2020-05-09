package cn.itlou.genutils.tabledoc;

import cn.itlou.genutils.model.TableDescModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 获取表数据
 *
 * @author yuanyl
 * @date 2020/5/8 21:48
 **/
@Mapper
public interface TableDescMapper {

    @Select("select a.attnum AS id,\n" +
            "c.relname AS tableName,\n" +
            "-- 这里是表描述,原本新建数据库的时候没有添加表描述,查询出来会为空,注释掉就好,有表描述的放开这条注释\n" +
            "cast(obj_description(relfilenode,'pg_class') as varchar) AS tableDesc,\n" +
            "a.attname AS fieldName,\n" +
            "concat_ws('',t.typname,SUBSTRING(format_type(a.atttypid,a.atttypmod) from '\\(.*\\)')) as fieldType,\n" +
            "d.description AS fieldDesc,\n" +
            "a.attnotnull canNull\n" +
            "from pg_class c, pg_attribute a , pg_type t, pg_description d\n" +
            "-- 这里是你的表名\n" +
            "where c.relname = #{tableName, mode = IN, jdbcType = VARCHAR}" +
            "and a.attnum > 0\n" +
            "and a.attrelid = c.oid\n" +
            "and a.atttypid = t.oid\n" +
            "and  d.objoid=a.attrelid\n" +
            "and d.objsubid=a.attnum\n" +
            "ORDER BY c.relname DESC,a.attnum ASC")
    List<TableDescModel> getTableInfo(String tableName);

    @Select("SELECT tablename\n" +
            "FROM pg_tables\n" +
            "WHERE tablename NOT LIKE 'pg%'\n" +
            "  AND tablename NOT LIKE 'sql_%'")
    List<String> getAllTableName();

}

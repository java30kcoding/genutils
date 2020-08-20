package cn.itlou.genutils.pg;

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
            "(select\n" +
            "cast(obj_description(max(relfilenode),'pg_class') as varchar) as comment from pg_class x\n" +
            "where x.relname = c.relname\n" +
            "group by relfilenode limit 1) as tableDesc,\n" +
            "a.attname AS fieldName,\n" +
            "concat_ws('',t.typname,SUBSTRING(format_type(a.atttypid,a.atttypmod) from '\\(.*\\)')) as fieldType,\n" +
            "d.description AS fieldDesc,\n" +
            "a.attnotnull canNull\n" +
            "from pg_class c, pg_attribute a , pg_type t, pg_description d\n" +
            "-- 这里是你的表名\n" +
            "where c.relname = #{tableName, mode = IN, jdbcType = VARCHAR} \n" +
            "and a.attnum > 0\n" +
            "and a.attrelid = c.oid\n" +
            "and a.atttypid = t.oid\n" +
            "and  d.objoid=a.attrelid\n" +
            "and d.objsubid=a.attnum\n" +
            "and relnamespace = (SELECT oid FROM pg_namespace WHERE nspname='zsxt')\n" +
            "ORDER BY c.relname DESC,a.attnum ASC")
    List<TableDescModel> getTableInfo(String tableName);

    @Select("select\n" +
            "c.relname AS tableName\n" +
            "from pg_class c, pg_attribute a , pg_type t, pg_description d\n" +
            "-- 这里是你的表名\n" +
            "where a.attnum > 0\n" +
            "and a.attrelid = c.oid\n" +
            "and a.atttypid = t.oid\n" +
            "and  d.objoid=a.attrelid\n" +
            "and d.objsubid=a.attnum\n" +
            "and relnamespace = (SELECT oid FROM pg_namespace WHERE nspname='zsxt')\n" +
            "group by tableName\n" +
            "ORDER BY tableName ASC")
    List<String> getAllTableName();

}

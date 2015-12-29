package ymy.com.mybatis.demo.utils;

import ymy.com.mybatis.demo.annotation.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yemengying on 15/11/9.
 */
public class SqlGenrateUtil {


    /**
     * get insert sql using anntation and reflection
     * @param obj
     * @param objName
     * @return sql
     */
    public static String getInsertSql(Object obj, String objName){

        if(null == obj) return "";
        StringBuilder filedSql = new StringBuilder("(");
        StringBuilder valueSql = new StringBuilder("value (");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            // If filed don't have Column annotation, continue
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            // get instance of Column annotation
            Column c = field.getAnnotation(Column.class);
            // get datebase's filed
            String columnName = c.name();
            // if name is empty, use attribute's name as database's field
            if ("".equals(columnName.trim())) {
                columnName = field.getName();
            }

            filedSql.append(columnName + ",");
            valueSql.append("#{" + objName + "." + field.getName() + "},");
        }
        //remove last ','
        valueSql.deleteCharAt(valueSql.length() - 1);
        filedSql.deleteCharAt(filedSql.length() - 1);
        valueSql.append(") ");
        filedSql.append(") ");

        return filedSql.toString() + " " + valueSql.toString();
    }


}

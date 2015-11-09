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
     * 根据反射和注解生成insert时sql语句
     * @param obj
     * @param objName
     * @return 返回map key field 对应的值是sql语句中数据库字段的部分 key value对应的值是插入时value的部分
     */
    public static Map<String,String> getAllPropertiesForSql(Object obj, String objName){

        Map<String,String> map = new HashMap<String,String>();
        if(null == obj) return map;
        StringBuilder filedSql = new StringBuilder("(");
        StringBuilder valueSql = new StringBuilder("value (");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 判断该成员变量上是不是存在Column类型的注解
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }

            Column c = field.getAnnotation(Column.class);// 获取实例
            // 获取元素值
            String columnName = c.name();
            // 如果未指定列名，默认列名使用成员变量名
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
        map.put("field",filedSql.toString());
        map.put("value", valueSql.toString());

        System.out.println("database filed sql: " + filedSql.toString());
        System.out.println("value sql:" + valueSql.toString());

        return map;
    }


}

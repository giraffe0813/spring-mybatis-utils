package ymy.com.mybatis.demo.dao;

import ymy.com.mybatis.demo.model.Poi;
import ymy.com.mybatis.demo.utils.SqlGenrateUtil;

import java.util.Map;

/**
 * Created by yemengying on 15/11/9.
 */
public class PoiSqlProvider {


    public String insertPoiBo(Map<String,Object> map){
        Poi poiBo = (Poi)map.get("poiBo");
        StringBuilder sql = new StringBuilder("insert into poi_shop ");
        //get sql via reflection and annotation
        Map<String,String> sqlMap = SqlGenrateUtil.getAllPropertiesForSql(poiBo, "poiBo");
        sql.append(sqlMap.get("field")).append(sqlMap.get("value"));
        System.out.println(sql.toString());
        return sql.toString();

    }


}

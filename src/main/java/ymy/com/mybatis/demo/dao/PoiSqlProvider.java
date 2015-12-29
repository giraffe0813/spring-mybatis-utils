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
        StringBuilder sql = new StringBuilder("insert into poi_shop ");//table name is poi_shop
        //get sql via reflection and annotation
        String insertSql = SqlGenrateUtil.getInsertSql(poiBo, "poiBo");
        return insertSql;

    }


}

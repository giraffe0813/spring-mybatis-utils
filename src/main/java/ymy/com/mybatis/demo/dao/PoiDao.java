package ymy.com.mybatis.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import ymy.com.mybatis.demo.model.Poi;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by yemengying on 15/11/9.
 */
public interface PoiDao {


    /**
     * 未使用注解的方法
     * @param poiBo
     * @return
     */
    @Deprecated
    @Insert("insert into poi_shop(name,brand,tags,status,phone,mobile,business_time,address,city,lng,lat,business_type,attribute_json) values(#{name},#{brand},#{tags},#{status},#{phone},#{mobile},#{business_time},#{address},#{city},#{lng},#{lat},#{business_type},#{attribute_json})")
    public int insertPoiInfo(Poi poiBo);




    @InsertProvider(type = PoiSqlProvider.class, method = "insertPoiBo")
    public int insertPoiInfoNew(@Param("poiBo")Poi poiBo);








}

package ymy.com.mybatis.demo.service.impl;

import ymy.com.mybatis.demo.dao.PoiDao;
import ymy.com.mybatis.demo.model.Poi;

import javax.annotation.Resource;

/**
 * Created by yemengying on 15/11/9.
 */
public class PoiService {

    @Resource
    private PoiDao poiDao;


    public int insertPoi(Poi poi){

       return  poiDao.insertPoiInfoNew(poi);

    }
}

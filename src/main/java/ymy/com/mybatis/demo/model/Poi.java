package ymy.com.mybatis.demo.model;

import ymy.com.mybatis.demo.annotation.Column;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 映射数据库的model
 * Created by yemengying on 15/11/9.
 */
public class Poi {

    @Column
    private BigInteger id;
    @Column(name = "poi_name")
    private String name;
    @Column(name = "poi_brand")
    private String brand;
    @Column
    private String tags;
    @Column
    private Integer status;
    @Column
    private String phone;
    @Column
    private String mobile;
    @Column
    private String businessTime;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private Double lng;
    @Column
    private Double lat;
    @Column
    private String businessType;
    @Column
    private String attributeJson;
    @Column
    private Timestamp updatedAt;
    @Column
    private Timestamp createdAt;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getAttributeJson() {
        return attributeJson;
    }

    public void setAttributeJson(String attributeJson) {
        this.attributeJson = attributeJson;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Poi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", tags='" + tags + '\'' +
                ", status=" + status +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", businessTime='" + businessTime + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", businessType='" + businessType + '\'' +
                ", attributeJson='" + attributeJson + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}

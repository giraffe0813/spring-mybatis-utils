# Spring-mybatis-utils

>This is a demo shows how to use SqlGenrateUtil class(a tool based on annotation and reflection that allows you to generate insert sql instead of writing long sql when use annotation-based mybatis). 

Assuming there is a PoiBo model with a lot of attributes.And we want to insert the instance of PoiBo into mysql database.

PoiBo.java
```java
public class PoiBo {

	private Long id;

	private String name;

	private String brand;

	private String tags;

	private Integer status;

	private String phone;

	private String mobile;

	private String business_time;

	private Float average_price;

	private String address;

	private String city;

	private Double lng;

	private Double lat;

	private String business_type;

	private String attribute_json;

	private Timestamp updated_at;

	private Timestamp created_at;
}
```



## without using utils

The following code snippet shows we must write long sql for a model with a lot of attributes without using utils.

```java
@Insert("insert into poi_shop(name,brand,tags,status,phone,mobile,business_time,address,city,lng,lat,business_type,attribute_json) values(#{name},#{brand},#{tags},#{status},#{phone},#{mobile},#{business_time},#{address},#{city},#{lng},#{lat},#{business_type},#{attribute_json})")
@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
public Long insertPoiInfo(PoiBo poiBo);
```
Writing long sql is boring and error prone.

## using utils

Now, we modify the PoiBo with Column annotation and use tool to help us generate sql.
```java
  public class PoiBo {

	@Column
	private Long id;//id represent the id filed in database
	@Column(name = "poi_name")
	private String name;//name represent the poi_name filed in database
	@Column(name = "poi_brand")
	private String brand;//brand represent the poi_brand filed in database
	@Column
	private String tags;
	@Column
	private Integer status;
	@Column
	private String phone;
	@Column
	private String mobile;
	@Column
	private String business_time;
	@Column
	private Float average_price;
	@Column
	private String address;
	@Column
	private String city;
	@Column
	private Double lng;
	@Column
	private Double lat;
	@Column
	private String business_type;
	@Column
	private String attribute_json;
	@Column
	private Timestamp updated_at;
	@Column
	private Timestamp created_at;
}

```
modify interface 

```java
@InsertProvider(type = PoiSqlProvider.class, method = "insertPoiBo")
public Long insertPoiInfo(@Param("poiBo")PoiBo poiBo);
```
create PoiSqlProvider.class and implement inserPoiBo method using SqlGenrateUtil class.The SqlGenrateUtil class has a method *getInsertSql* for generate insert sql based on model.

PoiSqlProvider.java

```java
    public String insertPoiBo(Map<String,Object> map){
        Poi poiBo = (Poi)map.get("poiBo");
        StringBuilder sql = new StringBuilder("insert into poi_shop ");//table name is poi_shop
        //get sql via reflection and annotation
        String insertSql = SqlGenrateUtil.getInsertSql(poiBo, "poiBo");
        return insertSql;

    }
```

So, using SqlGenrateUtil class we need add Column annotation on model's attibute , and call getInsertSql method to get insert sql. There is only one method in SqlGenrateUtil class , and it can be extend to generate update sql, select sql and so on.



 
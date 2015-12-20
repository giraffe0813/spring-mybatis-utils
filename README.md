# spring-mybatis-utils
一个利用注解和反射的工具类 避免在使用基于注解的mybatis时因为表字段太多而写过长的sql

>在开发时遇到一个问题，在使用基于注解的mybatis插入一个对象到mysql时，在写sql语句时需要列出对象的所有属性，所以在插入一个拥有10个以上属性的对象时sql语句就会变得很长，写起来也很不方便,也很容易拼错。google了一下也没有找到什么解决方式(可能是姿势不对😜)，在stackoverflow上提的[问题](http://stackoverflow.com/questions/33383134/how-to-insert-an-objectmore-than-10-properties-into-mysql-via-mybatis-based-on)截止目前还没有人回答。所以自己想了一个基于反射和注解的解决办法
博客地址：[基于注解的mybatis插入对象时利用反射生成sql语句](http://yemengying.com/2015/10/28/%E5%9F%BA%E4%BA%8E%E6%B3%A8%E8%A7%A3%E7%9A%84mybatis%E6%8F%92%E5%85%A5%E5%AF%B9%E8%B1%A1%E6%97%B6%E5%88%A9%E7%94%A8%E5%8F%8D%E5%B0%84%E7%94%9F%E6%88%90sql%E8%AF%AD%E5%8F%A5/)


下面是之前的代码片段:


```java
@Insert("insert into poi_shop(name,brand,tags,status,phone,mobile,business_time,address,city,lng,lat,business_type,attribute_json) values(#{name},#{brand},#{tags},#{status},#{phone},#{mobile},#{business_time},#{address},#{city},#{lng},#{lat},#{business_type},#{attribute_json})")
@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
public Long insertPoiInfo(PoiBo poiBo);
```
是不是too looooooooooooong


### 第一版（利用反射）

首先想到的是可以利用反射获得对象的所有属性，然后拼接成sql语句。所以写了一个基于反射拼装sql语句的方法，然后基于mybatis动态获得sql语句的方式 获得完整的sql 具体的代码如下:
接口层改为下面的样子，sql语句的生成放到PoiSqlProvider的insertPoiBo方法中

```java
@InsertProvider(type = PoiSqlProvider.class, method = "insertPoiBo")
public Long insertPoiInfo(@Param("poiBo")PoiBo poiBo);

```
PoiSqlProvider.class

```java
   public String insertPoiBo(Map<String,Object> map){
        PoiBo poiBo = (PoiBo)map.get("poiBo");
        StringBuilder sql = new StringBuilder("insert into poi_shop ");
        //get sql via reflection
        Map<String,String> sqlMap = getAllPropertiesForSql(poiBo, "poiBo");
        //
        sql.append(sqlMap.get("field")).append(sqlMap.get("value"));
        System.out.println(sql.toString());
        return sql.toString();

    }

	//根据传入的对象 基于反射生成两部分sql语句
    private  Map<String,String> getAllPropertiesForSql(Object obj, String objName){

        Map<String,String> map = new HashMap<String,String>();
         if(null == obj) return map;
        StringBuilder filedSql = new StringBuilder("(");
        StringBuilder valueSql = new StringBuilder("value (");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            filedSql.append(fields[i].getName() + ",");
            valueSql.append("#{" + objName + "." + fields[i].getName() + "},");
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

```

下面是基于反射生成的两部分sq语句和最后拼接的语句

```
database filed sql:
 
(id,name,brand,tags,status,phone,mobile,business_time,address,city,lng,lat,business_type,attribute_json,updated_at,created_at)
 
value sql:

value(#{poiBo.id},#{poiBo.name},#{poiBo.brand},#{poiBo.tags},#{poiBo.status},#{poiBo.phone},#{poiBo.mobile},#{poiBo.business_time},#{poiBo.address},#{poiBo.city},#{poiBo.lng},#{poiBo.lat},#{poiBo.business_type},#{poiBo.attribute_json},#{poiBo.updated_at},#{poiBo.created_at}) 

insert into poi_shop (id,name,brand,tags,status,phone,mobile,business_time,address,city,lng,lat,business_type,attribute_json,updated_at,created_at) value (#{poiBo.id},#{poiBo.name},#{poiBo.brand},#{poiBo.tags},#{poiBo.status},#{poiBo.phone},#{poiBo.mobile},#{poiBo.business_time},#{poiBo.address},#{poiBo.city},#{poiBo.lng},#{poiBo.lat},#{poiBo.business_type},#{poiBo.attribute_json},#{poiBo.updated_at},#{poiBo.created_at})

```
要注意的是如果数据库的字段名和插入对象的属性名不一致，那么不能使用生成的database filed sql。

### 最终版(加入注解)

上面的getAllPropertiesForSql方法有个缺点，如果数据库的字段名和类的属性名不一致，就不能依靠反射获得sql了。所以借鉴老大的ORM框架也写了一个注解Column，用于model类的属性上，表明属性所对应数据库字段。下面是Column注解的snippet。

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* 定义字段的注解*/
@Retention(RetentionPolicy.RUNTIME)
/*该注解只能用在成员变量上*/
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * 用来存放字段的名字 如果未指定列名，默认列名使用成员变量名
     *
     * @return
     */
    String name() default "";
	}

```

之后在model类属性上加入对应的注解,省略getter和setter。Column的name为空时，代表属性名和字段名一致。

```java

public class PoiBo {

	@Column
	private Long id;
	@Column(name = "poi_name")
	private String name;//表示name属性对应数据库poi_name字段
	@Column(name = "poi_brand")
	private String brand;//表示brand属性对应数据库poi_brand字段
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
修改getAllPropertiesForSql方法，通过获取类属性上的注解获得数据库字段名。

```java

private  Map<String,String> getAllPropertiesForSql(Object obj, String objName){

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

````
利用反射+注解之后的输出结果，可以看到sql语句正确按照name的Column注解的输出了name属性对应的数据库字段是poi_name.

```
database filed sql: 

(id,poi_name,poi_brand,tags,status,phone,mobile,business_time,average_price,address,city,lng,lat,business_type,attribute_json,updated_at,created_at) 

value sql:
value(#{poiBo.id},#{poiBo.name},#{poiBo.brand},#{poiBo.tags},#{poiBo.status},#{poiBo.phone},#{poiBo.mobile},#{poiBo.business_time},#{poiBo.average_price},#{poiBo.address},#{poiBo.city},#{poiBo.lng},#{poiBo.lat},#{poiBo.business_type},#{poiBo.attribute_json},#{poiBo.updated_at},#{poiBo.created_at}) 

insert into poi_shop 
(id,poi_name,poi_brand,tags,status,phone,mobile,business_time,average_price,address,city,lng,lat,business_type,attribute_json,updated_at,created_at) value (#{poiBo.id},#{poiBo.name},#{poiBo.brand},#{poiBo.tags},#{poiBo.status},#{poiBo.phone},#{poiBo.mobile},#{poiBo.business_time},#{poiBo.average_price},#{poiBo.address},#{poiBo.city},#{poiBo.lng},#{poiBo.lat},#{poiBo.business_type},#{poiBo.attribute_json},#{poiBo.updated_at},#{poiBo.created_at})

```





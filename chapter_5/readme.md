### 1.jdbcTemplate运行结果
记得给Book类写toString方法
技巧，
![](images/1.png)


快捷键技巧：
快速生成 get set toString 方法:    command + N


### 2.mybatis 整合
BookMapper和BookMapper.xml映射关系
![mapper和xml映射关系](images/xml_mapper_binding.png)
需要指定扫描的路径
![](images/xml_location.png)
运行结果:
![](images/2.png)

mybatis多表查询
![](images/mybatis多表查询.png)
![](images/多表查询运行结果.png)
### 3.springboot data jpa 整合
jpa实体和数据库表的对应关系
![](images/jpa实体和数据库表的对应关系.png)

dao层需要继承一个JpaRepository 接口

![](images/3.png)
运行结果
![](images/jpa_result.png)
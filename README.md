
# Springboot整合mybatis+mysql+dubbo #

_说明：在mac系统下进行整合，其中service对应provider， web对应consumer，model对应api_

![](https://img.shields.io/github/stars/pandao/editor.md.svg) 
![](https://img.shields.io/github/forks/pandao/editor.md.svg) 
![](https://img.shields.io/github/tag/pandao/editor.md.svg) 
![](https://img.shields.io/github/release/pandao/editor.md.svg) 
![](https://img.shields.io/github/issues/pandao/editor.md.svg) 
![](https://img.shields.io/bower/v/editor.md.svg)

## 目录 ##

<!-- vim-markdown-toc GFM -->

* [1. 安装zookeeper](#1-安装zookeeper)
* [2. 搭建dubbo-admin](#2-搭建dubbo-admin)
* [3. 新建数据库及表](#3-新建数据库及表)
* [4. 新建idea工程及实现](#4-新建idea工程及实现)
* [5. 启动provider,即service](#5-启动provider即service)
* [6. telnet测试provider服务](#6-telnet测试provider服务)
* [7. 启动consumer,即web](#7-启动consumer即web)
* [8. 启动dubbo-admin](#8-启动dubbo-admin)

<!-- vim-markdown-toc -->

## 1. 安装zookeeper ##
   ```java
    //查看zookeeper信息
    brew info zookeeper
    //安装
    brew install zookeeper
    //配置文件
    /usr/local/etc/zookeeper/
    //启动
    zkServer start
    //停止
    zkServer stop
    //查看状态
    zkServer status
   ```
   ![zookeeper](https://github.com/willpower88/spbdm/blob/master/doc/image/zookeeper-info.png)

## 2. 搭建dubbo-admin ##
   ```java
    //具体启动查看项目readme
    //https://github.com/apache/incubator-dubbo-admin
    git clone https://github.com/apache/incubator-dubbo-admin
   ```
## 3. 新建数据库及表 ##
   ```shell
   mysql -uroot -pyourpass < doc/gtest.sql
   ```
## 4. 新建idea工程及实现 ##
1. 新建spring工程 spbdm: File -> new -> project -> Sprint Initializr next ...
    ![new-project](https://github.com/willpower88/spbdm/blob/master/doc/image/new-project.png)
1. 删除src目录
1. 新建module spbdm-model 右击工程spbdm: new -> module -> Sprint Initializr  next ...
    ![new-module](https://github.com/willpower88/spbdm/blob/master/doc/image/new-module.png)
1. 新建module spbdm-web 右击工程spbdm: 同上
1. 新建module spbdm-service 右击工程spbdm: 同上
1. spbdm pom.xml增加moddle和spring-boot-maven-plugin增加configuration
    ```xml
	    <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        	<modelVersion>4.0.0</modelVersion>
        	<parent>
        		<groupId>org.springframework.boot</groupId>
        		<artifactId>spring-boot-starter-parent</artifactId>
        		<version>2.1.4.RELEASE</version>
        		<relativePath/> <!-- lookup parent from repository -->
        	</parent>
        	<groupId>com.willpower.spbdm</groupId>
        	<artifactId>spbdm</artifactId>
        	<version>0.0.1-SNAPSHOT</version>
        	<packaging>pom</packaging>
        	<name>spbdm</name>
        	<description>Demo project for Spring Boot</description>
        
        	<modules>
        		<module>spbdm-model</module>
        		<module>spbdm-service</module>
        		<module>spbdm-web</module>
        	</modules>
        
        	<properties>
        		<java.version>1.8</java.version>
        	</properties>
        
        	<dependencies>
        		<dependency>
        			<groupId>org.springframework.boot</groupId>
        			<artifactId>spring-boot-starter-jdbc</artifactId>
        		</dependency>
        		<dependency>
        			<groupId>org.springframework.boot</groupId>
        			<artifactId>spring-boot-starter-web</artifactId>
        		</dependency>
        		<dependency>
        			<groupId>org.mybatis.spring.boot</groupId>
        			<artifactId>mybatis-spring-boot-starter</artifactId>
        			<version>2.0.1</version>
        		</dependency>
        
        		<dependency>
        			<groupId>mysql</groupId>
        			<artifactId>mysql-connector-java</artifactId>
        			<scope>runtime</scope>
        		</dependency>
        		<dependency>
        			<groupId>org.springframework.boot</groupId>
        			<artifactId>spring-boot-starter-test</artifactId>
        			<scope>test</scope>
        		</dependency>
        		<dependency>
        			<groupId>com.alibaba</groupId>
        			<artifactId>druid</artifactId>
        			<version>1.1.6</version>
        		</dependency>
        		<dependency>
        			<groupId>io.dubbo.springboot</groupId>
        			<artifactId>spring-boot-starter-dubbo</artifactId>
        			<version>1.0.0</version>
        		</dependency>
        		<dependency>
        			<groupId>org.apache.zookeeper</groupId>
        			<artifactId>zookeeper</artifactId>
        			<version>3.4.8</version>
        		</dependency>
        	</dependencies>
        
        	<build>
        		<plugins>
        			<plugin>
        				<groupId>org.springframework.boot</groupId>
        				<artifactId>spring-boot-maven-plugin</artifactId>
        				<configuration>
        					<classifier>exec</classifier>
        				</configuration>
        			</plugin>
        		</plugins>
        	</build>
        
        </project>

    ```
1. spbdm-model
    + 更新pom.xml
       ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        	<modelVersion>4.0.0</modelVersion>
        	<parent>
        		<groupId>com.willpower.spbdm</groupId>
        		<artifactId>spbdm</artifactId>
        		<version>0.0.1-SNAPSHOT</version>
        	</parent>
        
        	<artifactId>spbdm-model</artifactId>
        
        	<properties>
        		<java.version>1.8</java.version>
        	</properties>
        
        	<dependencies>
        
        	</dependencies>
        
        	<build>
        		<plugins>
        			<plugin>
        				<groupId>org.springframework.boot</groupId>
        				<artifactId>spring-boot-maven-plugin</artifactId>
        			</plugin>
        		</plugins>
        	</build>
        
        </project>


       ```
    + 新建entity
       ```java
       package com.willpower.spbdm.model.entity;
       
       import java.io.Serializable;
       
       /**
        * Created by Powersoft on 2019/4/28.
        */
       public class User implements Serializable {
           
           private Integer id;
       
           private String name;
       
           private String passwd;
       
           private Integer sex;
       
           private String email;
       
           private String mobile;
       
           public Integer getId() {
               return id;
           }
       
       
           public void setId(Integer id) {
               this.id = id;
           }
       
           public String getName() {
               return name;
           }
       
       
           public void setName(String name) {
               this.name = name == null ? null : name.trim();
           }
       
       
           public String getPasswd() {
               return passwd;
           }
       
       
           public void setPasswd(String passwd) {
               this.passwd = passwd == null ? null : passwd.trim();
           }
       
       
           public Integer getSex() {
               return sex;
           }
       
       
           public void setSex(Integer sex) {
               this.sex = sex;
           }
       
       
           public String getEmail() {
               return email;
           }
       
       
           public void setEmail(String email) {
               this.email = email == null ? null : email.trim();
           }
       
       
           public String getMobile() {
               return mobile;
           }
       
           public void setMobile(String mobile) {
               this.mobile = mobile == null ? null : mobile.trim();
           }
       }


       ```
    + 新建Service
        ```java
            package com.willpower.spbdm.model.service;
            
            import com.willpower.spbdm.model.entity.User;
            
            import java.util.List;
            
            /**
             * Created by Powersoft on 2019/4/29.
             */
            public interface UserService {
            
                /**
                 * 获取所有用户
                 * @return
                 */
                List<User> findUserAll();
            
                /**
                 * 根据用户名获取用户
                 * @param name
                 * @return
                 */
                User findUserByName(String name);
            }

        ```
    
1. spbdm-service (对应provider)
    + 更新pom.xml
        ```xml
           <?xml version="1.0" encoding="UTF-8"?>
           <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
           	<modelVersion>4.0.0</modelVersion>
           	<parent>
           		<groupId>com.willpower.spbdm</groupId>
           		<artifactId>spbdm</artifactId>
           		<version>0.0.1-SNAPSHOT</version>
           	</parent>
           
           	<artifactId>spbdm-service</artifactId>
           
           	<properties>
           		<java.version>1.8</java.version>
           	</properties>
           
           	<dependencies>
           		<dependency>
           			<groupId>com.willpower.spbdm</groupId>
           			<artifactId>spbdm-model</artifactId>
           			<version>0.0.1-SNAPSHOT</version>
           		</dependency>
           	</dependencies>
           
           	<build>
           		<plugins>
           			<plugin>
           				<groupId>org.springframework.boot</groupId>
           				<artifactId>spring-boot-maven-plugin</artifactId>
           			</plugin>
           		</plugins>
           	</build>
           
           </project>

         ```
    + 创建Mapper
        ```java
        package com.willpower.spbdm.service.mapper;
        
        import com.willpower.spbdm.model.entity.User;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Select;
        
        import java.util.List;
        
        /**
         * Created by Powersoft on 2019/4/29.
         */
        @Mapper
        public interface UserMapper {
        
        
            @Select("select * from user")
            List<User> findUserAll();
        
            @Select("select * from user where name = #{name}")
            User findUserByname(@Param("name") String name);
        }

         ```
    + 创建Service实现类
        ```java
        package com.willpower.spbdm.service.impl;
        
        import com.alibaba.dubbo.config.annotation.Service;
        import com.willpower.spbdm.model.entity.User;
        import com.willpower.spbdm.model.service.UserService;
        import com.willpower.spbdm.service.mapper.UserMapper;
        import org.springframework.beans.factory.annotation.Autowired;
        
        import java.util.List;
        
        /**
         * Created by Powersoft on 2019/4/29.
         */
        @Service(version = "1.0.0")
        public class UserServiceImpl implements UserService {
        
            @Autowired
            UserMapper userMapper;
        
            @Override
            public List<User> findUserAll() {
                return userMapper.findUserAll();
            }
        
            @Override
            public User findUserByName(String name) {
                return userMapper.findUserByname(name);
            }
        }

        ```
    + application.properties 添加配置
        ```properties
            server.port=8081
            
            #mysql
            spring.datasource.url=jdbc:mysql://localhost:3306/gtest
            spring.datasource.username=root
            spring.datasource.password=qy
            spring.datasource.driverClassName=com.mysql.jdbc.Driver
            
            
            #dubbo
            spring.dubbo.application.name=spbdm-service
            spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
            spring.dubbo.protocol.name=dubbo
            spring.dubbo.protocol.port=20880
            spring.dubbo.scan=com.willpower.spbdm.service.impl
            
            
            #log
            logging.level.com.willpower.spbdm = debug

        ```

    + 启动类SpbdmServiceApplication不需要修改
       
1. spbdm-web (对应consumer)
    + 更新pom.xml
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        	<modelVersion>4.0.0</modelVersion>
        	<parent>
        		<groupId>com.willpower.spbdm</groupId>
        		<artifactId>spbdm</artifactId>
        		<version>0.0.1-SNAPSHOT</version>
        	</parent>
        
        	<artifactId>spbdm-web</artifactId>
        
        	<properties>
        		<java.version>1.8</java.version>
        	</properties>
        
        	<dependencies>
        		<dependency>
        			<groupId>com.willpower.spbdm</groupId>
        			<artifactId>spbdm-model</artifactId>
        			<version>0.0.1-SNAPSHOT</version>
        		</dependency>
        	</dependencies>
        
        	<build>
        		<plugins>
        			<plugin>
        				<groupId>org.springframework.boot</groupId>
        				<artifactId>spring-boot-maven-plugin</artifactId>
        			</plugin>
        		</plugins>
        	</build>
        
        </project>


        ```
    + 创建controller
        ```java
        package com.willpower.spbdm.web.controller;
        
        import com.willpower.spbdm.web.consumer.UserConsumerService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        import com.willpower.spbdm.model.entity.User;
        
        import java.util.List;
        
        
        /**
         * Created by Powersoft on 2019/4/28.
         */
        @RestController
        @RequestMapping("/user")
        public class UserController {
        
            @Autowired
            UserConsumerService userService;
        
            @RequestMapping("")
            public String hi() {
                return "hi,sam!!!";
            }
        
            @RequestMapping("/all")
            public List<User> findUserAll() {
                List<User> users  = userService.findUserAll();
                return users;
            };
        
            @RequestMapping("/{name}")
            public User findUserByName(@PathVariable("name") String name) {
                User user = userService.findUserByName(name);
                return user;
            }
        }

        ```
    + 创建Consumer端service，这步很重要，容易漏掉，增加这个Component springboot才能自动扫描到service，大坑
        ```java
        package com.willpower.spbdm.web.consumer;
        
        import com.alibaba.dubbo.config.annotation.Reference;
        import com.willpower.spbdm.model.entity.User;
        import com.willpower.spbdm.model.service.UserService;
        import org.springframework.stereotype.Component;
        
        import java.util.List;
        
        /**
         * Created by Powersoft on 2019/4/29.
         */
        @Component
        public class UserConsumerService {
        
            @Reference(version = "1.0.0")
            UserService userService;
        
            public List<User> findUserAll() {
                return userService.findUserAll();
            }
        
            public User findUserByName(String name) {
                return userService.findUserByName(name);
            }
        }

        ```

    + application.properties 添加配置
        ```properties

        server.port=8082
        
        #mysql
        spring.datasource.url=jdbc:mysql://localhost:3306/gtest
        spring.datasource.username=root
        spring.datasource.password=qy
        spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
        
        #dubbo
        spring.dubbo.application.name=spbdm-service
        spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
        spring.dubbo.protocol.name=dubbo
        spring.dubbo.scan=com.willpower.spbdm.web.consumer

        ```
## 5. 启动provider,即service ##
   ```html
    Idea启动即可，要先启动provider
   ```
   ![start-provider](https://github.com/willpower88/spbdm/blob/master/doc/image/start-provider.png)
## 6. telnet测试provider服务 ##
   ```java
   telnet 127.0.0.1 20880
   telnet> display
   dubbo>ls -l
   dubbo>invoke UserService.findUserByName("sam")
   {"passwd":"123","sex":1,"name":"sam","mobile":"13567899876","id":9,"email":"sam@tt.cn"}
   elapsed: 6 ms.
   ```

## 7. 启动consumer,即web ##
   ```html
    Idea启动即可，要先启动provider
   ```
   ![start-consumer](https://github.com/willpower88/spbdm/blob/master/doc/image/start-consumer.png)
## 8. 启动dubbo-admin ##
   + dubbo-admin, 进入incubator-dubbo-admin
        ```
        //可选
        mvn clean package
        cd dubbo-admin-distribution/target; java -jar dubbo-admin-0.1.jar
        ```

   ![dubbo-admin](https://github.com/willpower88/spbdm/blob/master/doc/image/dubbo-admin.png)

   + show success

   ![finduserbyname](https://github.com/willpower88/spbdm/blob/master/doc/image/finduserbyname.png)


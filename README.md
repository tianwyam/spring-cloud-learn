# spring-cloud-learn

<br/>



学习spring cloud知识积累



<br/>



整体采用maven的多模块构建工程方式，父工程引入spring cloud父依赖版本：

~~~xml

<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.6.RELEASE</version>
</parent>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Finchley.SR1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

~~~







<br/>



## 服务注册中心 eureka



<br>

工程 spring-cloud-learn-eureka 


eureka 注册服务中心



### 服务注册中心-服务端



<br>

<hr/>


第一步：引入 eureka 服务端依赖: pom.xml

~~~xml

<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>

~~~

<hr/>


第二步：配置eureka服务配置文件:  application.yml

~~~yaml

server:
    port: 8080
spring:
    application:
        name: eureka
eureka:
    client:
        register-with-eureka: false
        fetch-registry: false
        service-url:
            defaultZone: http://localhost:8080/eureka
~~~



eureka服务注册中心服务端也是 一个 eureka客户端，是一个不需要注册进入eureka服务端的客户端




<hr/>


第三步：启动类开启eureka服务中心注解: @EnableEurekaServer

~~~java

// 开启eureka服务注册中心配置
@EnableEurekaServer
@SpringBootApplication
public class LearnEurekaServerApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(LearnEurekaServerApplication.class, args);
	}
}

~~~

<hr/>


第四步：启动 浏览器访问 http://localhost:8080



![eureka-server启动服务图片：resource\img\eureka-server.jpeg](resource\img\eureka-server.jpeg)


<br/>

出现以上页面，表示 eureka 注册中心 服务端就成功了





### 服务注册中心-客户端



在微服务中，多个单体服务注册到服务注册中心去，方便其他服务发现自己，进行互相通信

<hr/>





第一步：添加依赖 pom.xml

~~~xml

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>

~~~



<hr/>



第二步：添加主配置 application.yml

~~~yaml
server:
  port: 9090
spring:
  application:
    name: books-service

eureka:
  client:
  	register-with-eureka: true
    serviceUrl:
      defaultZone: http://localhost:8080/eureka
~~~



客户端注册到eureka服务注册中心



<hr/>



第三步：启动类上添加 启动eureka客户端配置 @EnableEurekaClient



~~~java
@EnableEurekaClient
@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}
	
}

~~~



<hr/>



第四步：启动eureka服务端，再启动客户端后，界面可以看见此客户端服务注册信息



![books服务注册客户端](.\resource\img\books-service.jpg)





## 服务配置中心 config



在微服务中，各个应用各自配置维护是个麻烦事，config配置中心是统一管理各个应用配置，方便统一



### 配置中心服务端



第一步：创建一个git仓库专门管理各个应用的配置文件

仓库：spring-cloud-learn-git-config

仓库下创建应用的配置文件 books-service-dev.yml

内容：

~~~yaml
book:
  id: 202201*DEV
  name: 《萧十一郎 * DEV》
  author: 李寻欢DEV
~~~



<hr/>



第二步：添加依赖pom.xml

~~~xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
~~~



<hr/>

第三步：添加主配置 application.yml

~~~yaml
 
server:
  port: 7070
  servlet:
    context-path: /
    
  
spring:
  application:
    name: config-server
    
  cloud:
    config:
      server:
        git:
          uri: https://gitee.com/tianwyam/spring-cloud-learn-git-config.git
~~~



首先要建立一个存放配置文件的git仓库 spring-cloud-learn-git-config ，统一管理各个应用的配置文件

注意：若是配置文件git仓库是私有的，则需要配置用户名和密码，公共的则只需要配置URI地址即可

<br/>

springcloud-config 它配置文件规则是 ：

分支（label）-》应用名称(spring.application.name)-环境(profile).yml  或者是 properties文件

若是 spring.application.name 没有设置，则默认是 application

比如：master 下 config-server-DEV.yml



<hr/>

第四步：启动类上添加 开启config服务注解 @EnableConfigServer

~~~java
// 开启配置中心服务
@EnableConfigServer
@SpringBootApplication
public class LearnSpringCloudConfigApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LearnSpringCloudConfigApplication.class, args);
	}

}
~~~



最后启动应用后，就可以查看存放在git仓库里面的各个应用的配置文件

其实配置中心服务端就是一个映射git仓库的配置，进行统一管理

<br/>

访问：http://localhost:7070/{name}/{profile}/{label}

<br/>

比如：books-service 这个应用的配置

访问地址：http://localhost:7070/books-service/dev/master

结果：

~~~json
{
	"name": "books-service",
	"profiles": ["dev"],
	"label": "master",
	"version": "b765aa3e430758ac610906a85825c46e78686667",
	"state": null,
	"propertySources": [{
		"name": "https://gitee.com/tianwyam/spring-cloud-learn-git-config.git/books-service-dev.yml",
		"source": {
			"book.id": "202201*DEV",
			"book.name": "《萧十一郎 * DEV》",
			"book.author": "李寻欢DEV"
		}
	}, {
		"name": "https://gitee.com/tianwyam/spring-cloud-learn-git-config.git/application-dev.yml",
		"source": {
			"student.name": "李寻欢-DEV",
			"student.age": 26
		}
	}]
}
~~~







### 配置中心客户端



客户端就是各个应用配置服务端地址，在启动时会先去拉取配置中心里面的配置，优先采用



<hr/>

第一步：引入依赖 pom.xml

~~~xml
<!-- springcloud config client -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>

~~~

spring-cloud-starter-config 包含了 spring-cloud-config-client 的jar






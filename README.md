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




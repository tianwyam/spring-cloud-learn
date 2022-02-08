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



## spring-cloud-learn-eureka 服务注册中心



<br>



eureka 注册服务中心



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







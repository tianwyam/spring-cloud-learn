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



## 一、eureka 服务注册中心 



<br>

工程 spring-cloud-learn-eureka 


eureka 注册服务中心



### 1.1 eureka 服务端



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





### 1.2 eureka 客户端



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





## 二、spring cloud config 配置中心



在微服务中，各个应用各自配置维护是个麻烦事，config配置中心是统一管理各个应用配置，方便统一



### 2.1 config 服务端



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





### 2.2 config 客户端



客户端就是各个应用配置服务端地址，在启动时会先去拉取配置中心里面的配置，优先采用



<hr/>

1.引入依赖 pom.xml

~~~xml
<!-- springcloud config client -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>

~~~

spring-cloud-starter-config 包含了 spring-cloud-config-client 的jar



<hr/>



2.添加配置 bootstrap.yml

~~~yaml

spring:
  cloud:
    config:
      label: master
      profile: dev
      uri:
      - http://localhost:7070
~~~



注意：springcloud config的服务端的配置需要在 bootstrap.yml 或者 bootstrap.properties文件里面配置，这样项目启动最先加载此配置文件，然后去远端配置中心拉取系统所需要的配置

若是配置在application.yml文件中的话，config-server的配置无法生效

拉取各个应用的配置文件的规则是：applicationName-profile.yml 或者 applicationName-profile.properties



<hr/>

3.应用中使用配置

~~~java
@Data
@ConfigurationProperties(prefix = "book")
public class GitConfigBookBean {
	
	private String id ;
	
	private String name ;
	
	private String author ;
	
}
~~~

也可以使用 @Value("${book.id}") 方式取值



开启配置，读取配置文件

~~~java
@EnableEurekaClient
@SpringBootApplication
// 开启配置，读取配置文件
@EnableConfigurationProperties(value = GitConfigBookBean.class)
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}
	
}

~~~

方式很多种，都是可以去配置中心拉取到相应的配置的







## 三、feign的使用



在微服务下，用于多应用之间进行通信（各个应用需要注册到服务注册中心，方便查找各个应用的地址 HOST \ IP等）



其实跟 httpclient、RestTemplate等都是一类的，用于调用接口HTTP通信工具



feign可以像本地调用方法一样调用远程服务接口



<hr/>



1.添加依赖

~~~xml

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
~~~



<hr/>

2.添加配置

~~~yml
server:
  port: 8082
spring:
  application:
    name: user-service-feign

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8080/eureka
~~~



<hr/>



3.启动类上添加开启feign注解  @EnableFeignClients

~~~java
@SpringBootApplication
@EnableEurekaClient
// 开启feign注解
@EnableFeignClients
public class FeignUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignUserServiceApplication.class, args);
	}
}

~~~



<hr/>



4.编写feign客户端

~~~java
@FeignClient("books-service")
public interface BooksServiceFeignClient {

	@GetMapping("/book")
	public BookVo getBook() ;
	
}

~~~



此处的 books-service 会去 eureka服务注册中心去找相对应的应用的 HOST/PORT

至此，就可以像本地调用方法一样，调用远程服务接口



<hr/>



5.调用feign客户端应用，进行调用远程服务接口

~~~java
@RestController
@RequestMapping("/user")
public class UserController {
	
    // 调用远程服务接口的 feign客户端
	@Autowired
	private BooksServiceFeignClient booksServiceFeignClient ;
	
	@GetMapping("/book")
	public BookVo getBook() {
		System.out.println("feign");
        // 调用远程接口
		return booksServiceFeignClient.getBook() ;
	}
}

~~~





## 四、ribbon 负载均衡



Ribbon是一个客户端的负载均衡器，它决定选择哪一台机器来调用远程服务，对于多应用实例

<br/>



1.添加依赖

~~~xml

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
~~~





<hr/>



2.添加配置

~~~yml
server:
  port: 8081
spring:
  application:
    name: user-service

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8080/eureka
~~~



<hr/>

3.在启动类上添加开启 负载均衡 客户端注解 @RibbonClient

~~~java

@SpringBootApplication
@EnableEurekaClient
// 开启负载均衡
@RibbonClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	
}

~~~



<br/>



4.对 RestTemplate添加 负载均衡  @LoadBalanced 注解

~~~java
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
~~~



这样就可以对 RestTemplate  进行负载均衡 调用服务



<br/>



5.控制器 负载均衡调用服务

~~~java
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private RestTemplate restTemplate ;
	
	@GetMapping("/book")
	public BookVo getBook() {
		String url = "http://BOOKS-SERVICE/book" ;
		return restTemplate.getForObject(url, BookVo.class);
	}
}

~~~

<br/>

当调用 BOOKS-SERVICE 服务时，会去eureka服务注册中心获取具体的IP地址，进行请求，当有多个实例时，ribbon会实现负载均衡的去调用服务



<br/>

<hr/>

<br/>



### 4.1 负载均衡策略



ribbon 默认是采用的 轮询调度策略 RoundRobinRule



常用的负载均衡规则（IRule）有：

| 负载均衡策略         | 子类型                      | 负载均衡名称      |
|:---------------|--------------------------|-------------|
| RoundRobinRule |                          | 轮询调度（默认的）   |
|                | WeightedResponseTimeRule | 权重（不推荐，过时了） |
|                | ResponseTimeWeightedRule | 权重          |
| RandomRule     |                          | 随机分配        |
| RetryRule      |                          | 重试机制        |



​	





<hr/>



### 4.2 自定义负载均衡策略





1.添加配置类

~~~java
@Configuration
public class CostumRibbonConfig {

	@Bean
	public IRule ribbonRule() {
        // 自定义负载均衡的策略，此处采用的 随机策略
		return new RandomRule();
	}

}

~~~





2.启动类上添加 开启负载均衡配置 

~~~java
@SpringBootApplication
@EnableEurekaClient
// 开启ribbon负载均衡
// books-service 服务采用的是 CostumRibbonConfig 里面的负载均衡策略
@RibbonClient(name = "books-service", configuration = CostumRibbonConfig.class)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	
	
}

~~~



## 五、Hystrix 熔断器





Hystrix是一个断路器，它将服务调用进行隔离，用快速失败来代替排队，阻止级联调用失败。它的目的是不让服务挂掉

<br/>

防止 A->B->C 服务调用，C服务不可用时，A、B服务都不可用


<br/>

工程：spring-cloud-learn-hystrix-books-service



### 5.1 单独使用hystrix断路器


1.添加依赖pom.xml

~~~xml

<!-- hystrix 熔断器 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>

~~~

<br/>

2.启动类添加断路器注解 @EnableCircuitBreaker

~~~java
@EnableEurekaClient
// 开启断路器
@EnableCircuitBreaker
@SpringBootApplication
public class LearnHystrixApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnHystrixApplication.class, args);
	}
	
}
~~~

<br/>

3.在主服务添加短路服务 @HystrixCommand(fallbackMethod = "defaultBook")
~~~java

@RestController
@RequestMapping("/book")
public class BooksController {
	
	@Autowired
	private RestTemplate restTemplate ;
	
	@GetMapping({"","/"})
    // 配置 短路器回调执行方法
	@HystrixCommand(fallbackMethod = "defaultBook")
	public BookVo getBook() {
		return restTemplate.getForObject("http://localhost:9090/book/", 
                                         BookVo.class);
	}

	/**
	 * @description
	 *	短路器 默認執行方法
	 * @author TianwYam
	 * @date 2022年2月22日下午9:01:06
	 * @return
	 */
	public BookVo defaultBook() {
		return BookVo.builder()
				.name("《肖生克的救贖-DEFAULT》")
				.author("tianwyam").build();
	}
	
	
}

~~~

<br/>
当 getBook() 方法调不通时 http://localhost:9090/book/ 无法访问时，hystrix 会即使中断，采取备份方式执行 defaultBook() 方法

<br/>

输出：

~~~json
{
  "name": "《肖生克的救贖-DEFAULT》",
  "author": "tianwyam"
}
~~~



<br/>
注意：@HystrixCommand 添加断路器不止可以在controller中，还可以在service中进行配置

<br/>
<br/>
其原理是采用AOP方式：具体可以看源码  HystrixCommandAspect 



<br/>

<hr/>

<br/>



### 5.2 在feign中使用hystrix



<br/>

hystrix可以配合feign使用，在调用第三方服务失败时，可以执行默认回调方法

<br/>



1.添加依赖 pom.xml

~~~xml

<!-- hystrix 熔断器 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>

<!-- feign 配合 hystrix的使用 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
~~~



<br/>



2.添加配置 在feign中开启hystrix

~~~yaml
   
#开启hystrix熔断器
feign:
  hystrix:
    enabled: true
    
    
~~~

开启配置的源码：FeignClientsConfiguration 配置类上

~~~java

@Configuration
@ConditionalOnClass({ HystrixCommand.class, HystrixFeign.class })
protected static class HystrixFeignConfiguration {
    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.hystrix.enabled")
    public Feign.Builder feignHystrixBuilder() {
        return HystrixFeign.builder();
    }
}

~~~



<br/>



3.在feign客户端中添加回调

~~~java
// 开启feign客户端 
// fallback 配置回调
@FeignClient(value = "BOOKS-SERVICE", fallback = BookServiceFallBack.class)
public interface BookServiceFeignClient {
	
	@GetMapping("/book")
	public BookVo getBookVo();
	
	
}

~~~

<br/>

此处是 调用第三方服务 BOOKS-SERVICE 的 /book 方法 失败时，会执行 回调类 BookServiceFallBack 里面对应的 getBookVo() 方法 进行隔断，防止级联调用失败

<br/>



默认回调类 实现 BookServiceFeignClient 对应的方法

~~~java

/**
 * @description
 *	回调方法
 * @author TianwYam
 * @date 2022年2月24日下午7:18:53
 */
@Service
public class BookServiceFallBack implements BookServiceFeignClient{


	@Override
	public BookVo getBookVo() {
		return BookVo.builder()
				.name("《hystrix在feign中使用》-DEFAULT")
				.author("tianwyam")
				.build();
	}

	
}
~~~



<br/>

4.在controller层调用 feign 客户端

~~~java

@RestController
@RequestMapping("/book")
public class BooksController {
	
	@Autowired
	private BookServiceFeignClient bookServiceFeignClient;
	
	
	/**
	 * @description
	 *	采用feign方式调用
	 * @author TianwYam
	 * @date 2022年2月24日下午7:25:03
	 * @return
	 */
	@GetMapping("/feign")
	public BookVo getFeignBookVo() {
		return bookServiceFeignClient.getBookVo();
	}
}

~~~



<br/>



5.在启动类上添加注解 开启feign 和 短路器

~~~java
@EnableEurekaClient
// 开启断路器
@EnableCircuitBreaker
// 开启feign客户端
@EnableFeignClients
@SpringBootApplication
public class LearnHystrixApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnHystrixApplication.class, args);
	}
	
}

~~~

<br/>

采用feign方式时，feign会去eureka服务中心调用服务，若是服务 BOOKS-SERVICE 不可达时，hystrix会进行服务降级，调用默认执行方法 

结果：

~~~json
{
  "name": "《hystrix在feign中使用》-DEFAULT",
  "author": "tianwyam"
}
~~~





<br/>

<hr/>

<br/>

### 5.3 hystrix在feign里统一处理异常



<br/>

Hystrix使用FallbackFactory统一处理，查看服务调用异常或失败，可以获取熔断降级处理的原因

<br/>

1.在使用feign 客户端时配置回调工程类

~~~java

// 方式二 采用factory方式获取异常
@FeignClient(value = "BOOKS-SERVICE", fallbackFactory = BookServiceFallBackFactory.class)
public interface BookServiceFeignClient {
	
	@GetMapping("/book")
	public BookVo getBookVo();
	
}

~~~

<br/>

2.自定义回调工程类 实现 FallBackFactory 接口

~~~java

/**
 * 设置回调工厂类，可以获取错误提示
 */
@Component
public class BookServiceFallBackFactory implements FallbackFactory<BookServiceFeignClient> {

    @Override
    public BookServiceFeignClient create(Throwable e) {
        return new BookServiceFallBack(){
            @Override
            public BookVo getBookVo() {
                return BookVo.builder()
                        .name(String.format("《Hystrix回调获取异常之%s》", e.getMessage()))
                        .author("tianwyam")
                        .build();
            }
        };
    }
}

~~~

<br/>

当feign调用服务 BOOKS-SERVICE 出现异常或失败时，hystrix会执行回调工厂进行服务降级处理

结果：

~~~json
{
  "name": "《Hystrix回调获取异常之com.netflix.client.ClientException: Load balancer does not have available server for client: BOOKS-SERVICE》",
  "author": "tianwyam"
}
~~~



<br/>

<hr/>

<br/>



## 六、Zuul网关路由



<br>

Zuul是netflix组的一个子项目

之前学习了spring cloud提供给微服务系统的服务注册与发现、配置中心统一管理、断路器、负载均衡、服务间通信工具等，而Netflix提供了一个组件Zuul，它的作用有微服务网关，提供动态路由，访问过滤等服务

<br/>



Zuul组件的核心就是一系列的过滤器，这些过滤器有：

1.身份认证和安全: 识别每一个资源的验证要求，并拒绝那些不符的请求
2.审查与监控：
3.动态路由：动态将请求路由到不同后端集群
4.压力测试：逐渐增加指向集群的流量，以了解性能
5.负载分配：为每一种负载类型分配对应容量，并弃用超出限定值的请求
6.静态响应处理：边缘位置进行响应，避免转发到内部集群



<br/>

**没有网关路由之前：**

比如目前有两个服务：books-service、user-service

分别对应的有个资源请求需要对外提供的，比如：/books、/users 

若是没有网关路由的话，需要请求：http://books-service/books、http://user-service/users

服务消费者方需要维护两个服务的地址，若是现在需要加上认证，服务生产方需要对每个服务都需要添加认证的代码块，双方都比较头疼麻烦

**若是现在有了网关路由**，则统一对外管理API

比如 ：

请求网关服务：http://zuul-server/books-service/books  zuul服务就转发请求到 books-service 服务进行查询

请求网关服务：http://zuul-server/user-service/users  zuul服务就转发请求到 user-service 服务进行查询

若是需要统一添加认证，则在zuul服务中拦截一切请求，通过认证的进行转发，没有通过认证的，则直接返回，这样减少了多个微服务直接的服务IP暴露，并且外部是不需要知道内部微服务间通信的



<br/>



### 6.1 配置服务路由



<br/>

1.添加依赖

~~~xml

<!-- Netflix Zuul网关-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>

~~~

<br/>



2.添加服务路由

~~~yml


~~~



<br/>



3.启动类上添加开启路由代理注解 @EnableZuulProxy

~~~java

// 开启网关路由代理
@EnableZuulProxy
@SpringBootApplication
public class LearnZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnZuulApplication.class, args);
	}

}

~~~









## 七、gateway 网关







## 八、bus 总线







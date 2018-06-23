# springboot demo 
本程序基于以下软件或工具：
1. Java SDK 1.8 
2. MariaDB 10.1
3. IntelliJ IDEA 
4. Maven 3

## 目标
通过代码演示基于spring boot 2.0开发web服务器程序，并以目前了解情况，提供最佳实践，后续持续更新不足之处。
## 目录
### 第一部分
1. 基本介绍
2. 创建项目
3. 项目结构介绍和pom
4. Spring IOC 简单介绍和使用
5. 阿里巴巴Java开发规范及IDEA插件、IDEA代码质量提示 
6. 参数配置、自定义参数配置
7. JPA和自定义查询
8. 单元测试以及覆盖率
9. 数据库连接池扩展：Druid，并监控运行状况
10. Restful API开发：接收参数、验证参数、返回结果
11. 使用MockMvc进行单元测试
12. ObjectMapper扩展，处理LocalDate等数据类型
13. Restful API 开发进阶：返回统一数据结构和统一异常处理
14. Restful API 开发进阶：CROS
15. 多语言处理
16. 使用logback记录系统运行log
17. 支持web页面开发  
18. 配置访问静态资源
19. 部署
20. 性能测试 

### 第二部分：进阶开发 
1. 自定义类接收参数、参数加密
2. 自定义权限验证
3. 使用ehcache缓存数据
4. 使用redis缓存数据
5. rabbitmq使用
6. mongodb使用
7. mongodb扩展
8. 异步线程处理请求并设置java线程数相关参数
9. 性能监控


## 基本介绍
spring boot非常适合构建web应用程序，有如下优点：
* 入门门槛低，抛弃了以前的各种配置，开箱即用；
* 开源模块多，构建应用速度块；
* 社区活跃，可快速找到各种教程和坑的解决方案；
 
java界第一IDE,宇宙第二 IntelliJ IDEA 直追vs， 速度块，功能贴心、使用。

## 创建项目
1. 通过spring初始化器来添加项目，File -- New -- Project   

   ![选择spring](md/img/1/1.png)
    
2. 设置项目项目信息  
  ![](md/img/1/2.png)
    * 设置为maven的方式构建项目
    * Group 是项目组织唯一的标识符，实际对应JAVA的包的结构，是main目录里java的目录结构。
    * ArtifactID是项目的唯一的标识符，实际对应项目的名称，就是项目根目录的名称。
    * Version 版本号，SNAPSHOT为快照版本，一般在开发模式下，我们可以频繁的发布SNAPSHOT版本，以便让其它项目能实时的使用到最新的功能做联调；
  当版本趋于稳定时，再发布一个正式版本，供正式使用。当然在做正式发布时，也要确保当前项目的依赖项中不包含对任何SNAPSHOT版本的依赖，保证正式版本的稳定性。 [正式版和快照版本的区别](https://www.cnblogs.com/huang0925/p/5169624.html)
    * Package 包名，可以理解为命名空间；是main目录里java的目录结构。在java中，一个类的包名必须要目录结构一致，否则无法加载；
    * Group和ArtifactID使用**小写的方式**，因为它们都会对应的实际的目录结构，兼容不同操作系统对目录的大小写区别。
    * [maven官网文档中的命名约定指南](https://maven.apache.org/guides/mini/guide-naming-conventions.html)
    
3. 选择本项目依赖的包
 ![](md/img/1/3.png)
    * 需要按照项目的需求来选择最小的依赖，在之后可以按需添加依赖；
    * 在demo的pom.xml的文件中已经对每个包做了解释。

4. 创建好项目后，在IDEA的右下角会自动弹出窗口，点击设置自动导入前面选择的依赖包。 
  ![](md/img/1/4.png)
  
   也可以到设置界面选择，这样以后变更依赖包时会自动导入
  ![](md/img/1/6.png)
  
## 项目结构介绍和pom
1. 根据上一节创建的项目，会创建如下代码：
  ![](md/img/2/1.png)
    * main/java/包名：存放的业务代码；其中java下存放代码，resources目录下存放资源文件，如图片、配置档、css、html等；
    * test/java/包名: 存放多有的测试用例；**建议测试用例的目录结构和业务代码的结构保持一致**
    * pom.xml : POM是项目对象模型(Project Object Model)的简称,它是Maven项目中的文件,
    该文件用于管理：项目的依赖关系、源代码、配置文件、开发者的信息和角色、 组织信息、项目授权、项目的url、等等。事实上，在Maven世界中，只要有pom.xml文件就可以开始一个项目。
    * External Libraries: 当前项目所有依赖的jar包、jdk等运行基础；由maven自动更新其中的依赖包；

2. POM文件
    pom文件定义了项目的基本信息、依赖关系、编译部署行为等。详细请大家找相关资料；  
    Spring boot项目的pom文件必须有maven的插件，如果是多模块项目，那么只能是启动项目有此插件：
    ```
     <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId> 
     </plugin> 
    ```  
    我们在查询、更新、发布时，都会用到[maven的仓库网站](http://mvnrepository.com/)
 
## Spring IOC 简单介绍和使用
    Spring 入门请参照 [Spring 官方手册](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/)   
    推荐书籍： [Spring in Action](https://book.douban.com/subject/2282628/)  

1. 当我们添加Spring的相关包之后，我们就可以直接使用Spring的提供的服务;  
2. 我们自己创建服务则只需要在类上添加注解@Service或者@Component
    ```java
    /**
     * 发送邮件消息
     * @author chenxsa
     */
    @Service
    public class SendEmailMessageService implements ISendMessageService {
    }
    
    ```
    > @Service 是一种 @Component。 还有@Controller，@Repository都是一种@Component，用来在不同的场景下标记；
    
    > 服务或者组件默认是单例的；可以通过@Scope("prototype")指定多例，主要生命周期：prototype、request、session、global session

3.对于需要特殊构造函数的服务，我们需要通过配置的方式来提供服务。  
    
   ```java
   @Configuration
   public class ExtendConfig  { 
        /**
        * 提供对配置参数加密服务
        */
       @Bean(name = "encryptorBean")
       public StringEncryptor stringEncryptor() {
           PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
           SimpleStringPBEConfig config = new SimpleStringPBEConfig();
           config.setPassword("digiwin.dap.middleware");
           config.setAlgorithm("PBEWithMD5AndDES");
           config.setKeyObtentionIterations("1000");
           config.setPoolSize("1");
           config.setProviderName("SunJCE");
           config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
           config.setStringOutputType("base64");
           encryptor.setConfig(config);
           return encryptor;
       }
    }
   ``` 
   > @Configuration代表当前是一个服务配置类，在内部标记为@Bean的函数则是服务提供者。spring boot 大量采用这种方式

4.使用服务 
  ```
        @Autowired
        ISendMessageService sendMessageService;
    
        @Autowired
        @Qualifier("encryptorBean")
        StringEncryptor stringEncryptor;
 ```
  
## 阿里巴巴Java开发规范及IDEA插件、IDEA代码质量提示 

1. 推荐按照阿里巴巴的开发规范来做代码规范，并使用其插件进行代码扫描  

    [阿里巴巴java开发规范地址](https://github.com/alibaba/p3c/blob/master/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E8%AF%A6%E5%B0%BD%E7%89%88%EF%BC%89.pdf)  

    [IDEA插件安装](https://www.cnblogs.com/cnndevelop/p/7697920.html)

2. 在写代码的时候，我们可以通过IDEA的代码质量提示来调整代码  

   在写代码过程中，IDEA会动态扫描代码，识别代码的不规范之处，或者存在bug的地方，并使用黄色警告显示在右边。  
   
   ![](md/img/4/3.png)  
   ![](md/img/4/4.png)   
    
   我们要尽量把所有的黄色警告都消灭来提高代码质量
package com.xgstudio.springbootdemo.config;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xgstudio.springbootdemo.serializer.*;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author chenxsa
 * @Description 全局支持跨域访问api
 */
@Configuration
public class WebMvcConfigurationExtendConfig extends WebMvcConfigurationSupport {
    @Autowired
    private MessageSource messageSource;

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    /**
     * 自定义校验器
     * @return
     */
    @Override
    public org.springframework.validation.Validator getValidator() {
        return validator();
    }

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


    @Bean
    @Primary
    public ObjectMapper getObjectMapper(){
        return WebMvcConfigurationExtendConfig.createObjectMapper();
    }

    static ObjectMapper objectMapper =null;

    /**
     * 返回全局唯一的ObjectMapper
     *
     * @param
     * @return
     * @author chenxsa
     * @date 2018-5-16 15:38
     */
    public static ObjectMapper createObjectMapper(){
        if (objectMapper ==null) {
            synchronized (WebMvcConfigurationExtendConfig.class) {
                if (objectMapper == null) {
                    JavaTimeModule javaTimeModule = new JavaTimeModule();
                    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
                    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
                    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
                    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
                    javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
                    javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
                    javaTimeModule.addSerializer(Timestamp.class, new TimestampSerializer());
                    javaTimeModule.addDeserializer(Timestamp.class, new TimestampDeserializer());
                    objectMapper = Jackson2ObjectMapperBuilder.json()
                            .serializationInclusion(JsonInclude.Include.NON_NULL)
                            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                            .modules(javaTimeModule)
                            .build();
                }
            }
        }
        return objectMapper;
    }

    /**
     * 配置线程池
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(60 * 1000L);
        configurer.registerCallableInterceptors(timeoutInterceptor());
        configurer.setTaskExecutor(threadPoolTaskExecutor());
    }


    /**
     * 线程超时
     * @return
     */
    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    /**
     * 配置线程池大小
     * @param
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        //参照https://www.cnblogs.com/waytobestcoder/p/5323130.html
        /**
         * 需要根据几个值来决定
         * tasks ：每秒的任务数，假设为500~1000
         * taskcost：每个任务花费时间，假设为0.1s
         * responsetime：系统允许容忍的最大响应时间，假设为1s
         * 做几个计算
         * corePoolSize = 每秒需要多少个线程处理？
         * threadcount = tasks/(1/taskcost) =tasks*taskcout =  (500~1000)*0.1 = 50~100 个线程。corePoolSize设置应该大于50
         * 根据8020原则，如果80%的每秒任务数小于800，那么corePoolSize设置为80即可
         * queueCapacity = (coreSizePool/taskcost)*responsetime
         * 计算可得 queueCapacity = 80/0.1*1 = 80。意思是队列里的线程可以等待1s，超过了的需要新开线程来执行
         * 切记不能设置为Integer.MAX_VALUE，这样队列会很大，线程数只会保持在corePoolSize大小，当任务陡增时，不能新开线程来执行，响应时间会随之陡增。
         * maxPoolSize = (max(tasks)- queueCapacity)/(1/taskcost)
         * 计算可得 maxPoolSize = (1000-80)/10 = 92
         * （最大任务数-队列容量）/每个线程每秒处理能力 = 最大线程数
         * rejectedExecutionHandler：根据具体情况来决定，任务不重要可丢弃，任务重要则要利用一些缓冲机制来处理
         * keepAliveTime和allowCoreThreadTimeout采用默认通常能满足
         */
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(80);
        t.setQueueCapacity(100);
        t.setMaxPoolSize(200);
        t.setThreadNamePrefix("DAPThreadPool");
        return t;
    }

    /**
     * 功能描述:添加跨域访问
     * @param registry
     * @return void
     * @author chenxsa
     * @date 2018-5-12 11:06
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(false).maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    /**
     * 功能描述:添加默认语言
     * @param registry
     * @return void
     * @author chenxsa
     * @date 2018-5-12 11:06
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * 功能描述:  添加自定义转换器
     * @param converters
     * @return void
     * @author chenxsa
     * @date 2018-5-12 11:06
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add (mappingJackson2HttpMessageConverter ());
        super.configureMessageConverters (converters);
    }


    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        mappingJackson2HttpMessageConverter.setObjectMapper(getObjectMapper());
        //设置中文编码格式
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return mappingJackson2HttpMessageConverter;
    }


    @Bean
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(mappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

}

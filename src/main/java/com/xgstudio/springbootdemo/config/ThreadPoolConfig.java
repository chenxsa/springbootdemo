package com.xgstudio.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;

/**
 * @author chenxsa
 */
@Configuration
public class ThreadPoolConfig {
    /**
     *     参数初始化
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     *     核心线程数量大小
     */
    private static final int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    /**
     *     线程池最大容纳线程数
     */
    private static final int maximumPoolSize = CPU_COUNT * 2 + 1;

    /**
     *     线程空闲后的存活时长
     */
    private static final int keepAliveTime = 30;

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
        t.setCorePoolSize(corePoolSize);
        t.setQueueCapacity(corePoolSize*2);
        t.setMaxPoolSize(maximumPoolSize);
        t.setKeepAliveSeconds(keepAliveTime);
        t.setThreadNamePrefix("DAPThreadPool");
        t.initialize();
        return t;
    }



}

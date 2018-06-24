package com.xgstudio.springbootdemo.util;

public class SnowFlake {

    /**
     * Singleton
     */
    private static SnowFlake ourInstance = new SnowFlake();
    public static SnowFlake getInstance() {
        return ourInstance;
    }
    private SnowFlake() {
        // Default is 1,1
        this.datacenterId = 1;
        this.machineId = 1;
    }

    /**
     * 起始的时间戳
     */
    private final static long START_STAMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     * SEQUENCE_BIT 序列号占用的位数
     * MACHINE_BIT 机器标识占用的位数
     * DATACENTER_BIT 数据中心占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    private final static long MACHINE_BIT = 5;
    private final static long DATACENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastStamp = -1L;


    /**
     * Setup DatacenterId & MachineId. Default is 1,1
     * @param datacenterId
     * @param machineId
     */
    public void init(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }

        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * @return New SnowFlake Id
     */
    public synchronized long newId() {
        long currStamp = getNewstamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStamp;

        return (currStamp - START_STAMP) << TIMESTAMP_LEFT
                | datacenterId << DATACENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {
        long mill = getNewstamp();
        while (mill <= lastStamp) {
            mill = getNewstamp();
        }
        return mill;
    }

    private long getNewstamp() {
        return System.currentTimeMillis();
    }
}
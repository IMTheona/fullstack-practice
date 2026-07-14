package com.theona.utils;

/**
 * ThreadLocal工具类
 * 用于在同一线程中共享数据
 */
public class ThreadLocalUtil {

    // 提供ThreadLocal对象
    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 存储值
     * @param value 要存储的数据
     */
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    /**
     * 根据键获取值
     * @param <T> 返回值的类型
     * @return 存储的数据
     */
    @SuppressWarnings("unchecked")
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    /**
     * 清除ThreadLocal防止内存泄漏
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}

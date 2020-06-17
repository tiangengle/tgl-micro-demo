package com.tgl.eurekaclient.utils;

/**
 * FileName: ThreadLocalUtil
 * Author:   ssy
 * Date:     2019/10/25 4:06 下午
 * Description: 线程变量共享
 */
public class ThreadLocalUtil {
    private static final ThreadLocal threadLocal = new ThreadLocal();

    public static <T> void set(T t){
        threadLocal.set(t);
    }

    public static <T> T get(){
        return (T) threadLocal.get();
    }
    public static void remove(){
        threadLocal.remove();
    }
}

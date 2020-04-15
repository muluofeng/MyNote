package com.example.readwrite;

/**
 * @author xiexingxing
 * @Created by 2020-04-14 17:31.
 */
/**
 * 把数据源作为线程局部变量，线程使用完清除
 * Created by mahengyang on 2017/7/24.
 */
public class DynamicDataSourceHolder {
    private static final ThreadLocal<MyDataSource> holder = new ThreadLocal<MyDataSource>();

    private DynamicDataSourceHolder() {
    }

    public static void putDataSource(MyDataSource dataSource){
        holder.set(dataSource);
    }

    public static MyDataSource getDataSource(){
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

    public enum MyDataSource {
        READ, WRITE;
    }
}
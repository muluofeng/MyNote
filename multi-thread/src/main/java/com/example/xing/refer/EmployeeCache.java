package com.example.xing.refer;

import java.util.Hashtable;

/**
 * @author xiexingxing
 * @Created by 2020-06-17 17:28.
 */
public class EmployeeCache {
    // 一个 Cache 实例
    private static volatile EmployeeCache cache;
    // 用于 Cache 内容的存储
    private Hashtable<String, Employee> employeeRefs;
    // 构建一个缓存器实例
    private EmployeeCache() {
        employeeRefs = new Hashtable<>();
    }

    // 取得缓存器实例
    public static EmployeeCache getInstance() {
        if (cache == null) {
            synchronized (EmployeeCache.class) {
                if (cache == null) {
                    cache = new EmployeeCache();
                }
            }
        }
        return cache;
    }

    private void cacheEmployee(Employee em) {
        employeeRefs.put(em.getId(), em);
    }

    public Employee getEmployee(String id) {
        Employee em = null;
        if (employeeRefs.containsKey(id)) {
            em  = employeeRefs.get(id);
        }
        if (em == null) {
            em = new Employee(id);
            System.out.println("Retrieve From EmployeeInfoCenter. ID=" + id);
            cacheEmployee(em);
        }
        return em;
    }






    public static void main(String[] args) {
        EmployeeCache employeeCache = getInstance();
        Employee employee1 = employeeCache.getEmployee("11111");
        System.out.println("employee1：" + employee1.getName());
        employee1 = null;
        Employee employee2 = employeeCache.getEmployee("11111");
        System.out.println("employee2：" + employee2.getName());
        employee2 = null;
        System.gc();

        try {
            //jvm 参数  -Xmx10m -Xms10m
            byte[] bytes = new byte[5 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            Employee employee3 = employeeCache.getEmployee("11111");
            System.out.println("employee3：" + employee3.getName());
        }


    }
}
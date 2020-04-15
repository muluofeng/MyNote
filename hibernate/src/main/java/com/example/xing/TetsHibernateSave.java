package com.example.xing;

import org.hibernate.Session;

/**
 * @author xiexingxing
 * @Created by 2018-12-09 12:30 AM.
 */
public class TetsHibernateSave {
    public static void main(String[] args) {


        /**
         * 对持久性实体的任何更改都会自动保存,即使是在save之后
         */

        Session sessionOne = HibernateUtil.getSessionFactory().openSession();
        sessionOne.beginTransaction();

        //Create new Employee object
        EmployeeEntity emp = new EmployeeEntity();
        emp.setFirstName("Lokesh");
        emp.setLastName("Gupta");
        emp.setEmail(Math.random() + "xxx@qq.com");

        //Save employee
        sessionOne.save(emp);
        Integer id = emp.getEmployeeId();

        emp.setLastName("temp");

        sessionOne.getTransaction().commit();

        //Let's see what got updated in DB
        Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
        sessionTwo.beginTransaction();

        EmployeeEntity employee = (EmployeeEntity) sessionTwo.load(EmployeeEntity.class, id);
        System.out.println(employee.getLastName());  //temp

        sessionTwo.getTransaction().commit();
        HibernateUtil.shutdown();

        /**
         * hibernate的 4种状态
         * 1. Transient Object  用new创建的对象，它没有持久化，没有处于Session中，处于此状态的对象叫临时对象
         * 2. Persistent Object 已经持久化，加入到了Session缓存中，如通过hibernate语句保存的对象。处于此状态的对象叫持久对象
         * 3. Detached Object   持久化对象脱离了Session的对象，如Session缓存被清空的对象
         *                      特点：已经持久化，但不在Session缓存中。处于此状态的对象叫游离对象
         * 4. Removed Object
         */

        /**
         * 注意：
         *   在操作了hibernate的方法如save()等后，并没有直接生成sql语句，去操作数据库，
         *   而是把这些更新存入Session中，只有Session缓存要被更新时，底层的sql语句才能执行，数据存入数据库；
         *
         *   下面举例说明：
         * 1.Session.save(user)运行机理。
         * 1）把User对象加入缓存中，使它变成持久化对象；
         * 2）选用映射文件指定的标识生成ID；
         * 3）在Session清理缓存时候执行：在底层生成一个insert sql语句，把对象存入数据库；
         *
         *  警告： 在你执行Session.save(user)后，在Session清理缓存前，
         *         如果你修改user对象属性值，那么最终存入数据库的值将是最后修改的值；此过程中ID不能被修改；
         */


    }
}

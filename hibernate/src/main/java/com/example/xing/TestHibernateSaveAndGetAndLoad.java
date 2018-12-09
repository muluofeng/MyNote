package com.example.xing;

import org.hibernate.Session;

/**
 * 测试hibernater save and load and get
 *
 * @author xiexingxing
 * @Created by 2018-12-08 5:24 PM.
 */

public class TestHibernateSaveAndGetAndLoad {

    public static void main(String[] args) {
        Session sessionOne = HibernateUtil.getSessionFactory().openSession();
        sessionOne.beginTransaction();


        /**
         *  测试 load 方法
         */
        // 保存 一个实体对象
        EmployeeEntity emp = new EmployeeEntity();
        emp.setEmail("999@qq.com" + Math.random());
        emp.setFirstName("Lokesh");
        emp.setLastName("Gupta");

        //Save employee
        sessionOne.save(emp);
        //store the employee id generated for future use
        Integer empId = emp.getEmployeeId();
        sessionOne.getTransaction().commit();

        /************************************************************************/

        // 1. 查询一个实体数据   session.load(entity.class, ID )
        Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
        sessionTwo.beginTransaction();

        //first load() method example
        EmployeeEntity emp1 = (EmployeeEntity) sessionTwo.load(EmployeeEntity.class, empId);
        System.out.println(emp1.getFirstName() + " - " + emp1.getLastName());

        //Let's verify the entity name
        System.out.println(sessionTwo.getEntityName(emp1));

        sessionTwo.getTransaction().commit();

        /************************************************************************/

        Session sessionThree = HibernateUtil.getSessionFactory().openSession();
        sessionThree.beginTransaction();

        // 2. 查询一个实体数据   session.load("类的包名全路径", ID )
        EmployeeEntity emp2 = (EmployeeEntity) sessionThree.load("com.example.xing.EmployeeEntity", empId);
        System.out.println(emp2.getFirstName() + " - " + emp2.getLastName());

        sessionThree.getTransaction().commit();

        /************************************************************************/

        // 3. 查询一个实体数据   session.load("空的实体对象", ID )
        Session sessionFour = HibernateUtil.getSessionFactory().openSession();
        sessionFour.beginTransaction();

        //third load() method example
        EmployeeEntity emp3 = new EmployeeEntity();
        sessionFour.load(emp3, empId);
        System.out.println(emp3.getFirstName() + " - " + emp3.getLastName());

        sessionFour.getTransaction().commit();


        /**
         *  测试 get 方法
         *  get和load方法之间的区别在于当数据库中不存在标识符时的返回值。
         *
         * 在get()方法的情况下，我们将获得返回值，NULL就像标识符不存在一样。
         * 但是在load()方法的情况下，我们将获得运行时异常。
         */
        Session sessionFive = HibernateUtil.getSessionFactory().openSession();
        sessionFive.beginTransaction();
        EmployeeEntity load;
        EmployeeEntity get;
        try {
            load = (EmployeeEntity) sessionFive.load(EmployeeEntity.class, 12); //异常
            get = (EmployeeEntity) sessionFive.get(EmployeeEntity.class, 12);    // null

        } catch (Exception e) {
            e.printStackTrace();
        }
        sessionFive.getTransaction().commit();


        HibernateUtil.shutdown();
    }
}
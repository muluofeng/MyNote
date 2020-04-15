package com.example.xing;

import org.hibernate.Session;

/**
 * @author xiexingxing
 * @Created by 2018-12-08 11:42 PM.
 */
public class TestHibernateRefreshAndMerge {
    public static void main(String[] args) {

        /**
         * 有时我们面临的情况是应用程序数据库被一些外部应用程序/代理修改，
         * 因此应用程序中相应的hibernate实体实际上与它的数据库表示不同步，
         * 即具有旧数据。在这种情况下，您可以使用session.refresh()方法使用数据库中可用的最新数据重新填充实体
         * 1. refresh 表示从数据库重新加载数据
         * 2. merge   将更改的实体数据持久化到数据库
         */

        Session sessionOne = HibernateUtil.getSessionFactory().openSession();
        sessionOne.beginTransaction();

        //Create new Employee object
        EmployeeEntity emp = new EmployeeEntity();
        emp.setFirstName("Lokesh");
        emp.setLastName("Gupta");
        emp.setEmail(Math.random() + "Gupta@qq.com");

        //Save employee
        sessionOne.save(emp);
        sessionOne.getTransaction().commit();
        sessionOne.close();

        Integer saveId = emp.getEmployeeId();
        //Verify employee's firstname
        System.out.println(verifyEmployeeFirstName(saveId, "Lokesh"));

        Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
        sessionTwo.beginTransaction();

        //This
        emp.setFirstName("Vikas");
        sessionTwo.refresh(emp);  //从数据库重新加载实体数据

        sessionTwo.getTransaction().commit();
        sessionTwo.close();

        System.out.println(emp.getFirstName().equals("Lokesh"));

        /**
         *  merger 合并
         */

        Session sessionThree = HibernateUtil.getSessionFactory().openSession();
        sessionThree.beginTransaction();

        //This
        emp.setFirstName("Vikas");
        sessionThree.merge(emp);  //将更改的实体数据持久化到数据库

        sessionThree.getTransaction().commit();
        sessionThree.close();

        System.out.println(emp.getFirstName().equals("Lokesh"));

        HibernateUtil.shutdown();
    }

    /**
     * 验证 该id的数据的名字和传入的是否一致
     *
     * @param employeeId
     * @param firstName
     * @return
     */
    private static boolean verifyEmployeeFirstName(Integer employeeId, String firstName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        EmployeeEntity employee = (EmployeeEntity) session.load(EmployeeEntity.class, employeeId);
        //Verify first name
        boolean result = firstName.equals(employee.getFirstName());
        session.close();
        //Return verification result
        return result;
    }
}

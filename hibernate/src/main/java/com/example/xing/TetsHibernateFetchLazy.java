package com.example.xing;

import org.hibernate.Session;

/**
 * @author xiexingxing
 * @Created by 2018-12-09 12:00 AM.
 */
public class TetsHibernateFetchLazy {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //Add new Employee object
        EmployeeEntity emp = new EmployeeEntity();
        emp.setEmail("demo-user@mail.com");
        emp.setFirstName("demo");
        emp.setLastName("user");
        //Save entity
        session.save(emp);

        session.getTransaction().commit();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //Get only the reference of EmployeeEntity for now
//        EmployeeEntity empGet = (EmployeeEntity) session.byId( EmployeeEntity.class ).getReference( 1 );

        System.out.println("No data initialized till now; Lets fetch some data..");


        //Now EmployeeEntity will be loaded from database when we need it
//        System.out.println(empGet.getFirstName());
//        System.out.println(empGet.getLastName());

        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }
}

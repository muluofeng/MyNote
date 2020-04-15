package com.example.xing.practice;

import java.sql.*;

/**
 * @author xiexingxing
 * @Created by 2019-05-26 11:19 AM.
 */
public class jdbcTest {

    public static void main(String[] args) {
        useJDBC();
    }


    public static void useJDBC() {
        //使用jdbc 连接数据库
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/hibernatetest", "root", "root");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Employee where ID=?");
            preparedStatement.setInt(1, 19);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + resultSet.getString("EMAIL"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


}

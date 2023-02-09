package com.mayikt.servlet;

import com.mayikt.utils.MayiktJdbcUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * user register RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //backend acquire front-end data name="userName" & name="userPwd"
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        String userName = req.getParameter("userName");
        String userPwd = req.getParameter("userPwd");
        // use jdbc api to insert data into database
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = MayiktJdbcUtils.getConnection();
            //is userName already exist?
            String querySql="select * from `kyledb`.`servlet_users` where `userName`=?;";
            preparedStatement = connection.prepareStatement(querySql);
            preparedStatement.setString(1,userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){ //whether the next line has data
                writer.println("The user name already exists, please change another one");
                return;
            }
            //if not exist, insert data into database
            MayiktJdbcUtils.beginTransaction(connection); //start transaction
            String insertSql="insert into `kyledb`.`servlet_users`(`id`, `userName`,`userPwd`) values(null,?,?);\n";
            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,userPwd);
            int result = preparedStatement.executeUpdate();
            String resultStr = result>0?"registration success":"registration fail";
            writer.println(resultStr);
            MayiktJdbcUtils.commitTransaction(connection); //commit transaction
        }catch (Exception e){
            //rollback transaction
            MayiktJdbcUtils.rollBackTransaction(connection);
            writer.println("There is some problem with the system, please try again later");
        }finally {
            //release resource
            MayiktJdbcUtils.closeConnection(null,preparedStatement,connection);
            if(writer!=null){
                writer.close();
            }
        }

    }
}

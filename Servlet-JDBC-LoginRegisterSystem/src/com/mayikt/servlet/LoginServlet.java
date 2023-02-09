package com.mayikt.servlet;

import com.mayikt.utils.MayiktJdbcUtils;
import com.mysql.cj.util.StringUtils;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //backend acquire front-end data name="userName" & name="userPwd"
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        String userName = req.getParameter("userName");
        if(StringUtils.isEmptyOrWhitespaceOnly(userName)){
            writer.println("user name cannot be empty");
            return;
        }
        String userPwd = req.getParameter("userPwd");
        if(StringUtils.isEmptyOrWhitespaceOnly(userPwd)){
            writer.println("password cannot be empty");
            return;
        }
        //use jdbc api to query data from database
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = MayiktJdbcUtils.getConnection();preparedStatement = connection.prepareStatement("select * from `kyledb`.`servlet_users` where `userName`=? and `userPwd`=?;");
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,userPwd);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){ //whether the next line has data
                writer.println(userName+"login success");
                return;
            }
            writer.println("login fail! Wrong user name or password");

        }catch (Exception e) {
            writer.println("There is some problem with the system, please try again later");
        }finally {
            if(writer!=null){
                writer.close();
            }
            MayiktJdbcUtils.closeConnection(resultSet,preparedStatement,connection);

        }

    }
}

package controller;


import model.bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by  Nigel on 2016/4/18.
 */
@MultipartConfig
public class login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Statement stmt = (Statement) getServletContext().getAttribute("stmt");

        PrintWriter writer = resp.getWriter();
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        String useCookie = req.getParameter("useCookie");
        String query = "SELECT * FROM fs WHERE name='" + name + "' and password='" + password + "'";
        //// TODO: 2016/4/28 sql injection test
        System.out.println(query);


        try {
            if (stmt == null) {
                throw new SQLException("Something bad happened to the server, Try again later!");
            }
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) throw new Exception("Incorrect username or password!");

            User user1 = new User(rs.getInt("id"), rs.getString("name"), rs.getString("nickname"),
                                  rs.getString("email"), rs.getString("password"), rs.getString("tel"));
            session.setAttribute("login", true);
            session.setAttribute("user", user1);

            if (useCookie != null && useCookie.length() > 0) {
                //// TODO: 2016/4/24 加密
                Cookie usernameCookie = new Cookie("name", name);
                Cookie passwordCookie = new Cookie("password", password);

                usernameCookie.setMaxAge(60 * 60 * 24 * 10);
                passwordCookie.setMaxAge(60 * 60 * 24 * 10);

                resp.addCookie(usernameCookie);
                resp.addCookie(passwordCookie);
            } else {
                Cookie[] cookies = req.getCookies();

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("name") || cookie.getName().equals("password")) {
                            cookie.setMaxAge(0);
                            resp.addCookie(cookie);
                        }
                    }
                }
            }
            //服务器端跳转不可行，因为使用的ajax，发送重定向之后业之后被ajax接收处理，只能在客户端用Js跳转
            //resp.sendRedirect("/home");
            //返回的json的编码总是被改成ISO-8859-1，不知道怎么回事儿
            writer.println("{\"success\":\"OK\"}");

        } catch (Exception e) {
            writer.println("{\"error\":\"" + e.getMessage() + "\"}");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }
}

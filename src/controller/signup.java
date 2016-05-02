package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

/**
 * Created by  Nigel on 2016/4/19.
 */
public class signup extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Statement stmt = (Statement) getServletContext().getAttribute("stmt");
        req.setCharacterEncoding("utf-8");
        String name = URLDecoder.decode(req.getParameter("name"), "utf-8");
        String password = URLDecoder.decode(req.getParameter("password"), "utf-8");
        String email = URLDecoder.decode(req.getParameter("email"), "utf-8");
        String nickname = URLDecoder.decode(req.getParameter("nickname"), "utf-8");
        String tel = req.getParameter("tel");

        System.out.println("Post: " + req.getQueryString());
        String insert = "INSERT INTO fs (name,password,email,nickname,tel) VALUES('" + name + "','" + password + "','" + email + "','" +
                        nickname + "','" + tel + "')";
        System.out.println(insert);
        try {
            int ret = stmt.executeUpdate(insert);
            if (ret != 1) {
                throw new SQLException("Cannot insert new user");
            }
        } catch (SQLException e) {
            //throw new ServletException(e.getMessage());
            e.printStackTrace();
        }

        //带着参数跳转到过渡页
        req.setAttribute("title", "恭喜你,注册成功!");
        req.setAttribute("msg", "即将跳转到登陆页面...");
        req.getRequestDispatcher("/jsp/transient.jsp").forward(req, resp);
    }

    @Override
    //ajax验证用户名和邮箱是否已存在
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Statement stmt = (Statement) getServletContext().getAttribute("stmt");

        PrintWriter writer = resp.getWriter();
        Enumeration<String> names = req.getParameterNames();

        String q = names.nextElement();
        String v = req.getParameter(q);
        if (q == null || v == null) {
            writer.println("false");
            return;
        }

        if (q.equals("code")) {
            String genCode = (String) req.getSession().getAttribute("code");
            if (!genCode.equals(v)) {
                writer.println("\"False code!\"");
                return;
            }
            writer.println("true");
            return;
        }

        String query = "SELECT * FROM fs WHERE " + q + "='" + v + "'";
        System.out.println(query);
        System.out.println("Req: " + req.getQueryString());

        try {
            ResultSet rs = stmt.executeQuery(query);
            //如果不为空
            if (rs.next()) {
                writer.println("\"'" + v + "' has been taken, please try another.\"");
            } else {
                writer.println("true");
            }
        } catch (SQLException | NullPointerException e) {
            writer.println("\"Server feels not good, try again later.\"");
        }
    }
}

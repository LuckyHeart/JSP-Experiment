package controller;

import model.bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by  Nigel on 2016/4/13.
 */
@WebServlet(name = "/bean")
public class BeanTest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Person p = new Employee();
        //Employee p = new Employee();
        //p.setName("Evan");
        User user = new User();
        user.setName("Jiang Hang");
        user.setNickname("John");
        HttpSession session = req.getSession();
        session.setAttribute("login", true);        session.setAttribute("user", user);
        req.getRequestDispatcher("/jsp/beanTest.jsp").forward(req, resp);
    }
}

package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by  Nigel on 2016/4/18.
 */
public class db implements ServletContextListener {

    private static Connection con;
    private static Statement stmt;

    @Override
    public void contextInitialized(ServletContextEvent event) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/neu?useUnicode=true&characterEncoding=UTF8",
                                              "nigel", "nigel");

            //con.prepareStatement("");
            stmt = con.createStatement();
            event.getServletContext().setAttribute("stmt", stmt);
            System.out.println("Connected to MySQL.");

        } catch (SQLException | ClassNotFoundException e) {
            //监听器会在应用启动是被加载执行，所以，启动的时候就会抛出异常
            //但是因为无法抛出比父方法更多的异常，所以必须在方法内处理所有的异常，所以这里抛出的异常都不会跳到error-page
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        try {
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

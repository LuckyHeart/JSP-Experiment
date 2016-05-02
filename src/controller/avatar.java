package controller;

import model.bean.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by  Nigel on 2016/4/18.
 */
public class avatar extends HttpServlet {

    //可以看到这里的doGet/doPost都显示抛出了两个异常，就表明如果有当前方法无法处理的异常，就会把异常对象传递到error-page
    //如果error-page开启了isErrorPage，也就可以使用exception隐式对象来获取这个异常对象
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //上传的头像统一命名为avatar.png
        String avatar = "";
        try {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");
            String path = "/res/avatar/";
            File dir = new File(getServletContext().getRealPath(path));
            if (dir.exists()) {
                avatar = path + getFile(dir, user.getId() + "");
            }
        } catch (Exception e) {
            avatar = "/res/avatar/default.png";
        }
        // getResource只能以相对于Web应用的路径来获取资源，也就是说这个方法是用来获取Web管理的资源，然后返回给客户端的
        // getRealPath则是获取虚拟映射URL相对应的实际的磁盘上的物理路径，是为了使用File这些类
        InputStream in = getServletContext().getResourceAsStream(avatar);
        resp.setContentType(getServletContext().getMimeType(avatar));

        OutputStream out = resp.getOutputStream();
        byte[] bytes = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(bytes)) != -1) {
            out.write(bytes, 0, bytesRead);
        }
    }

    String getFile(File dir, String filename) {

        String[] names = dir.list();
        if (names != null) {
            for (String name : names) {
                if (name.split("\\.")[0].equals(filename)) {
                    return name;
                }
            }
        }
        return "/default.png";
    }
}

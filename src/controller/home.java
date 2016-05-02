package controller;

import model.bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by  Nigel on 2016/4/11.
 */
//@WebServlet(name = "changeFace")
@MultipartConfig
public class home extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        session.removeAttribute("result");
        ImageHandle handle = new ImageHandle(session);

        String option = getContent(request.getPart("option"));
        Part image = request.getPart("image");

        Path path = getSavePath(session, image);
        Files.copy(image.getInputStream(), path);

        switch (option) {
        case "combinePic":
            //好像写入的操作都没权限
            //永远不要使用getRealPath方法，不要保存到Web部署的目录里
            String img = (String) session.getAttribute("img");
            if (img == null) {
                session.setAttribute("img", path.toString());
            } else {
                session.removeAttribute("img");
                session.setAttribute("result", handle.combine(img, path.toString()));
                System.out.println("两张照片都收到了，已经合成了");
            }
            break;
        case "toBlackAndWhite":
            String result = handle.convertToBlockandWhile(path.toString());
            session.setAttribute("result", result);
            System.out.println("已转成黑白照");
            break;
        case "rotateLeft":
            session.setAttribute("result", handle.rotate(path.toString(), 0));
            break;
        case "rotateRight":
            session.setAttribute("result", handle.rotate(path.toString(), 1));
            break;
        case "rotate180":
            session.setAttribute("result", handle.rotate(path.toString(), 2));
            break;
        default:
        }
        //for (Part part : request.getParts()) {
        //    writer.print(part.getName() + " : ");
        //    writer.print(part.getSubmittedFileName() + " : ");
        //    writer.println(part.getSize());
        //    String filename = part.getSubmittedFileName() == null ? part.getName() : part.getSubmittedFileName();
        //    part.write(filename);
        //
        //}
        String json = "{\"initialPreview\": [\"<img src='/result?" + Math.random() + "' class='file-preview-image' " +
                      "alt='头像' " +
                      "title='头像'>\"],\"append\":true}";
        writer.println(json);
    }

    private String getContent(Part part) throws IOException {

        StringBuilder builder = new StringBuilder();
        InputStream in = part.getInputStream();
        int c;
        while ((c = in.read()) != -1) {
            builder.append((char) c);
        }
        in.close();
        return builder.toString();
    }

    private Path getSavePath(HttpSession session, Part filePart) throws FileNotFoundException {

        User user = (User) session.getAttribute("user");
        String id = "visitors";
        if (user != null) {
            id = "" + user.getId();
        }
        //HttpServlet中继承GenericServlet的getInitParameter方法获取的ServletConfig里的配置
        String path = getServletContext().getInitParameter("UPLOAD-ROOT");
        File file = new File(path, id);
        if (!file.exists()) {
            if (!file.mkdir()) {
                throw new FileNotFoundException("Cannot create new dir");
            }
        }
        String filename = filePart.getName() + "-" + System.currentTimeMillis() + "-" + filePart.getSubmittedFileName();
        return new File(file, filename).toPath();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //转发之后，原来的缓存区会被清空
        //response.getWriter().write("hello, changeFace servlet.");

        //dispatcher中的路径:如果url不以/开头的相对URL，那么会相对于原来的URL地址，
        //如果以/开头的绝对路径，那么会相对于当前整个Web引用的URL为基础
        //使用sendRedirect之后，会返回302 Found
        //使用重定向，浏览器的地址栏会改变，使用请求转发地址栏则没有变化

        //当前这个Servlet在web.xml中配置的URL是/jsp/changeFace
        //使用绝对路径，相对Web根目录
        //RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/index.jsp");
        //使用相对路径，由于此Servlet的虚拟目录与转发到的404.jsp在同一个目录中，所以可以转发到。
        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);

        //使用resp.sendRedirect()中的路径，如果为相对路径(不以/开始)，则基于原先Servlet或者JSP的URL建立完整的绝对URL
        //如果是以/开始的绝对路径，则相对于Web应用本身(也就是http://domain/app-name/,这个是应用根目录)建立完整的URL。
        //所以也就说：Servlet和JSP在写的时候放在了不同的物理目录中，但是可以通过url map弄到相同的虚拟目录中。就像真的放在了相同的目录中一样。
    }
}

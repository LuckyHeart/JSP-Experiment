package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * Created by  Nigel on 2016/5/2.
 */
@WebServlet(name = "result", urlPatterns = "/result")
public class result extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String result = (String) req.getSession(false).getAttribute("result");
        result = result == null ? getServletContext().getInitParameter("UPLOAD-ROOT") + "visitors/default.jpg" : result;
        File file = new File(result);
        resp.setHeader("Content-Type", getServletContext().getMimeType(result));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        //resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        System.out.println("result: " + result);
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}

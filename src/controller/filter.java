package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by  Nigel on 2016/5/2.
 */
@WebFilter(filterName = "notLogin", urlPatterns = "/*")
public class filter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //登录才能访问selfSpace页
        String uri = req.getRequestURI();
        if (uri.endsWith("selfSpace")) {
            HttpSession session = req.getSession();
            if (session.getAttribute("login") == null) {
                //如果没有登录，则重定向到登录页，且带上要求先登录的提示信息
                resp.sendRedirect("/login?loginFirst=true");
                System.out.println("Redirected: " + uri);
                return;
            }
        }

        req.setCharacterEncoding("utf-8");
        filterChain.doFilter(req, resp);
        System.out.println("Exclude: " + uri);
    }

    @Override
    public void destroy() {

    }
}

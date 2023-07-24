package org.jflores.apiservlet.webapp.headers.controllers;

import org.jflores.apiservlet.webapp.headers.services.LoginService;
import org.jflores.apiservlet.webapp.headers.services.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {

    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService auth = new LoginServiceImpl();
        Optional<String> cookieOptional = auth.getUsername(req);

        if (cookieOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("       <head>");
                out.println("               <meta charset=\"UTF-8\">");
                out.println("               <title>Hola " + cookieOptional.get() + "</title>");
                out.println("       </head>");
                out.println("       <body>");
                out.println("               <h1>Hola " + cookieOptional.get() + " has iniciado sesion con exito!</h1>");
                out.println("<p><a href='" + req.getContextPath() + "/index.html'>volver</a></p>");
                out.println("<p><a href='" + req.getContextPath() + "/logout'>Cerrar Sesion</a></p>");
                out.println("       </body>");
                out.println("</html>");
            }
        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {

            Cookie usernameCookie = new Cookie("username", username);
            resp.addCookie(usernameCookie);

            resp.sendRedirect(req.getContextPath()+"/login.html");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "usuario no autorizado");
        }
    }
}

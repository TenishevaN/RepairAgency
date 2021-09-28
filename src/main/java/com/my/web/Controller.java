package com.my.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.my.command.*;
import org.apache.logging.log4j.LogManager;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code Controller} class represents realization of the Command patern.
 * It's the only one servlet that depends on the request command parameter executes the corresponding actions.
 */

@WebServlet("/controller")
public final class Controller extends HttpServlet {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(Controller.class);


    public Controller() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String address = doProcess(req, resp);
        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String address = doProcess(req, resp);
        resp.sendRedirect(address);
    }

    private String doProcess(HttpServletRequest req, HttpServletResponse resp) {

        String address = "error.jsp";
        String commmandName = req.getParameter("command");
        log.debug("commmandName ==> " + commmandName);
        Command command = CommandContainer.getCommmand(commmandName);
        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            req.setAttribute("errorMessage", ex.getMessage());
        }
        return address;
    }
}

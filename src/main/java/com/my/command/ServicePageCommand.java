package com.my.command;

import com.my.Path;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code ReportPageCommand} class represents the implementation of the command to show Service page.
 */
public class ServicePageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return Path.SERVICE_PAGE;
    }
}

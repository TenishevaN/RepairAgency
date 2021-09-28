package com.my.command;

import com.my.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@ code DeleteUserCommand} class represents the implementation of the command to open the Error page.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        return Path.PAGE_ERROR_PAGE;
    }
}

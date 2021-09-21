package com.my.command;

import com.my.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

           return Path.PAGE_ERROR_PAGE;
    }
}

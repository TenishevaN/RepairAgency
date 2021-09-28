package com.my.command;

import javax.servlet.http.*;

/**
 * {@ code Command} class represents Command API.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public interface Command {

    String execute(HttpServletRequest req, HttpServletResponse resp);

}

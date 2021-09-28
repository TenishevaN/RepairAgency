package com.my.command;

import javax.servlet.http.*;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code Command} class represents Command API.
 */
public interface Command {
	
	String execute(HttpServletRequest req, HttpServletResponse resp);

}

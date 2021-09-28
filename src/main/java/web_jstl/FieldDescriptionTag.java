package web_jstl;

import org.apache.logging.log4j.LogManager;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * @author Tenisheva N.I.
 * @version 1.0
 * {@ code FieldDescriptionTag} class represents the description tag to display description for the different roles.
 */
public class FieldDescriptionTag extends SimpleTagSupport {

    private String descriptionText;
    private String nameRole;
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FieldDescriptionTag.class);

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public void doTag() throws JspException {
        String output = "";
        JspWriter out = getJspContext().getOut();

        if (("user".equals(nameRole))) {
            output = "<textarea rows=10 class=form-control name = description id = descriptionValue>" + descriptionText + "</textarea>";

        } else {
            output += descriptionText;
        }
        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}


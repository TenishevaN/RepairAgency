package web_jstl;

import com.my.ServiceUtil;
import com.my.db.dao.ReviewDAO;
import com.my.db.model.Review;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@ code ReviewSectionTag} class represents the custom tag to display review section.
 * <br>
 *
 * @author Tenisheva N.I.
 * @version 1.0
 */
public class ReviewSectionTag extends SimpleTagSupport {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ReviewSectionTag.class);

    private int idRepairRequest;
    private String currentLocale;

    public void setIdRepairRequest(String idRepairRequest) {
        this.idRepairRequest = Integer.parseInt(idRepairRequest);
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

    @Override
    public void doTag() {

        String output = "";
        JspWriter out = getJspContext().getOut();
        ReviewDAO reviewDAO = new ReviewDAO();
        List<Review> reviews = reviewDAO.getAllByRequestId(idRepairRequest);
        String reviewLabel = ServiceUtil.getKey("reviews", currentLocale);

        if (reviews.size() == 0) {
            return;
        }

        output += "<p>" + reviewLabel + ":</p>";
        for (Review item : reviews) {
            output += "<div >";
            output += "<time  class=date-answer > " + getFormatedDate(item.getDate()) + "</time >";
            output += "</div >";
            output += "<p class=ask-info >";
            output += item.getComment();
            output += "</p >";
        }
        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    private String getFormatedDate(Date date){
        SimpleDateFormat dt1 = new SimpleDateFormat("dd.MM.yyyy");
        String dateFormatted = dt1.format(date);
        return dateFormatted;
    }
}


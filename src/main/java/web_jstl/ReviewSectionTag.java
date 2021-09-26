package web_jstl;

import com.my.ServiceUtil;
import com.my.db.dao.ReviewDAO;
import com.my.db.model.Review;
import org.apache.logging.log4j.LogManager;

import javax.mail.Session;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

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
    public void doTag() throws JspException {
        String output = "";
        JspWriter out = getJspContext().getOut();
        ReviewDAO reviewDAO = new ReviewDAO();
        List<Review> reviews = reviewDAO.getAllByRequestId(idRepairRequest);
        String reviewLabel = ServiceUtil.getKey("reviews", currentLocale) ;

        if (reviews.size() == 0){
            return;
        }

        output +=  "<p>"+reviewLabel+":</p>";
       for(Review item : reviews) {
           output +=  "<div >";
           output +=    "<time  class=date-answer > " + item.getDate() + "</time >";
           output +=    "</div >";
           output +=    "<p class=ask-info >";
           output +=    item.getComment();
           output +=    "</p >";
       }
        try {
            out.println(output);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}


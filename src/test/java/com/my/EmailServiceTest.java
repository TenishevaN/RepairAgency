package com.my;

import com.my.db.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailServiceTest {

    @Test
    public void sendMailTest() {

        User user = new User();
        user.setEmail("servicemailtest2021@gmail.com");
        EmailService emailService = new EmailService(user, "en");
        boolean sent = emailService.sendMail();
        assertEquals(true, sent);
    }

}
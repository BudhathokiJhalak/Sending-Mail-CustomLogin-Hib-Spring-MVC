/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.sampleweb.controller;

import com.leapfrog.sampleweb.dao.CourseDAO;
import com.leapfrog.sampleweb.dao.EnquiryDAO;
import com.leapfrog.sampleweb.entity.Enquiry;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author zak
 */
@Controller
@RequestMapping(value = "/")
public class DefaultController {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private EnquiryDAO enquiryDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/enquiry", method = RequestMethod.GET)
    public String enquiry(Model model) {
        model.addAttribute("courses", courseDAO.getAll());
        return "enquiry";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String send(@ModelAttribute("enquiry") Enquiry enquiry) {
        enquiryDAO.insert(enquiry);
        return "redirect:/";
    }

    @RequestMapping(value = "/testmail", method = RequestMethod.GET)
    public String testMail() {
        String to = "bibek.thapahere@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "asd@gmail.com";

        // Assuming you are sending email from localhost
        String host = "smtp.wlink.com.np";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return "index";
    }
}
